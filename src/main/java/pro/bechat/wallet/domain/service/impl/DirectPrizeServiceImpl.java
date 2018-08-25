package pro.bechat.wallet.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.bechat.wallet.domain.dao.*;
import pro.bechat.wallet.domain.model.model.*;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.DictionaryVo;
import pro.bechat.wallet.domain.model.vo.DirectPrizeVo;
import pro.bechat.wallet.domain.model.vo.WalletVo;
import pro.bechat.wallet.domain.service.DirectPrizeService;
import pro.bechat.wallet.publics.ObjectUtils;
import pro.bechat.wallet.publics.RewardConfigureUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 福利奖ServiceImpl
 */
@Service
public class DirectPrizeServiceImpl implements DirectPrizeService {


    private Logger logger = Logger.getLogger("DirectPrizeServiceImpl");

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");         //日期格式

    private BigDecimal dayLimit = null;                  //接出币种数量

    @Autowired
    private DirectPrizeMapper directPrizeMapper;        //用户直推人记录Mapper

    @Autowired
    private WalletMapper walletMapper;                  //钱包信息Mapper

    @Autowired
    private DictionaryMapper dictionaryMapper;          //数据字典Mapper

    @Autowired
    private TranscationMapper transcationMapper;        //订单交易Mapper

    @Autowired
    private UserMapper userMapper;                      //用户信息Mapper

    @Autowired
    private InvestmentMapper investmentMapper;          //钱包对应投资信息

    @Override
    public void timedTask() throws Exception {

        interestAward();     //静态奖

        directPrize();       //直推奖

        dynamicAward();      //动态奖

    }


    /**
     * 币种生息 用户3天内未提取产生的利息，则不再生息
     * @return
     * @throws Exception
     */
    @Transactional
    public ApiResponseResult interestAward() throws Exception {

        BigDecimal staticGreaterNumber = RewardConfigureUtils.getInstance().getStaticGreaterNumber();       //10000 静态本金大于
        BigDecimal staticGreaterRation = RewardConfigureUtils.getInstance().getStaticGreaterRation();       //0.5 静态本金大于百分比,
        BigDecimal staticLessNumber = RewardConfigureUtils.getInstance().getStaticLessNumber();             //5000 静态本金小于,
        BigDecimal staticLessRation = RewardConfigureUtils.getInstance().getStaticLessRation();             //0.3 静态本金小于百分比,


        // 1、锁钱包 表  行级锁
        walletMapper.lockWalletTable();

        // 2、查询钱包信息,冻结金额不为 0 的  冻结金额就是用来生息的,  如果3天未提取利息的不再生息
        List<Wallet> walletList = walletMapper.selectUserWalletInterest();

        if(null == walletList){

            return ApiResponseResult.build(2012,"error","未查询到未超过3天提取利息的钱包信息","");
        }




        // 3、查询数据字典表,查询出相对于的手续费用 id 为 8,为利息生成
       /* List<DictionaryVo> dictionaryVoList = dictionaryMapper.selectDictionaryListById(8,"WALFARE_MODE");

        if(null == dictionaryVoList){

            return ApiResponseResult.build(2012,"error","未查询到数据字典内容信息","");
        }*/

        //DictionaryVo dictionaryVo = dictionaryVoList.get(0);
        //BigDecimal deductionPrice = new BigDecimal(dictionaryVo.getValue());      //生息比例
        BigDecimal deductionPrice = null;           //生息比例

        Wallet wallet = null;                       //钱包对象

        Integer num = null;                         //接收受影响的行数

        BigDecimal sum = null;                         //计算总和

        // 4、for循环 为有数据的钱包生息
        for(Wallet walletInfo : walletList){

            // 3、比较本金 于 定义的静态的利息规则
            // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
            int greaterThan = walletInfo.getAmount().compareTo(staticGreaterNumber);            //拿静态奖值
            //int lessThan = walletInfo.getAmount().compareTo(staticLessNumber);

            // 静态本金奖
            if(greaterThan == -1 || greaterThan == 0){

                deductionPrice = staticLessRation.divide(new BigDecimal("100"));                  //0.3/100
            }else if(greaterThan == 1 || greaterThan == 0){

                deductionPrice = staticGreaterRation.divide(new BigDecimal("100"));               //0.5/100
            }

            try{
                // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
                int compare = sum.compareTo(dayLimit);

                wallet = new Wallet();
                wallet.setUserId(walletInfo.getUserId());                           //用户编号
                //wallet.setCoinId(walletInfo.getCoinId());                           //币种

                if(compare == 1 || compare == 0){

                    wallet.setAmount(dayLimit);                                         //要生息的数量,超过今日放币数或者等于,将剩余的币数量放进去
                }else{

                    wallet.setAmount(walletInfo.getAmount().multiply(deductionPrice));  //要生息的数量  正常生息
                }

                // 5、查询用户钱包币种信息
                //Wallet userWallet = walletMapper.selectUserWalletByCoinId(wallet.getUserId(),wallet.getCoinId());
                //if(null == userWallet){

                //    continue;
               // }

                // 6、查询用户钱包币种相对应的 投资信息
                //Investment investment = investmentMapper.selectInvestmentByWalletId(userWallet.getId());
               // if(null == investment){

               //     continue;
               // }

                // 6、修改用户生息信息
               // wallet.setId(userWallet.getId());                              //钱包编号
                num = walletMapper.modifyUserWalletInterest(wallet);           //用户钱包币种利息生息

                // 7、转入利息信息
               // investment.setInterests(wallet.getAmount());                   //生息数量
               // investment.setRecommend(null);                                 //将直推奖金额设置为空
               // investment.setDynamicAward(null);                              //将动态奖金额设置为空
                //num = investmentMapper.modifyInvestmentInfo(investment);
                //if(num == 0){

                //   return ApiResponseResult.build(2010,"error","新增失败","");
                //}

                // 8、转入生息记录
                //num = insertWalletInterestToChargeTo(userWallet,wallet.getAmount());
                //if(num == 0){

                // return ApiResponseResult.build(2010,"error","新增失败","");
                // }

            }catch (Exception e){

                e.printStackTrace();
                //logger.warning("用户编号:"+wallet.getUserId()+",币种编号:"+wallet.getCoinId()+
                //        ",利息生息多少:"+wallet.getAmount()+",当前时间:"+sdf.format(new Date()));
            }

        }

        return ApiResponseResult.build(200,"success","今天利息生息已完成",num);
    }

    /**
     * 直推奖 按推荐人不同币种转入本金的30%释放，每天释放0.5%，直到释放完（只限3人）
     * @return
     * @throws Exception
     */
    @Transactional
    public ApiResponseResult directPrize() throws Exception {

        BigDecimal recommendLockRation = RewardConfigureUtils.getInstance().getRecommendLockRation();       //30 推荐锁仓比列百分比,
        BigDecimal recommendUnLockRation = RewardConfigureUtils.getInstance().getRecommendUnLockRation();   //0.5 推荐每天解仓数量百分比,
        Integer recommendNumber = RewardConfigureUtils.getInstance().getRecommendNumber();                  //3 推荐人数限制,

        // 1、锁钱包 表  行级锁
        walletMapper.lockWalletTable();

        // 2、查询直推记录表,未返还完的继续返还 当 本金不等于 已用金额
        List<DirectPrizeVo> voList =  directPrizeMapper.selectDirectPrizeList();

        if(null == voList){

            return ApiResponseResult.build(2012,"error","未查询到直推奖返还","");
        }

        // 3、查询数据字典表,查询出相对于的手续费用 id 为 9,为直推奖
        List<DictionaryVo> dictionaryVoList = dictionaryMapper.selectDictionaryListById(9,"WALFARE_MODE");

        if(null == dictionaryVoList){

            return ApiResponseResult.build(2012,"error","未查询到数据字典内容信息","");
        }

        DictionaryVo dictionaryVo = dictionaryVoList.get(0);
        BigDecimal deductionPrice = new BigDecimal(dictionaryVo.getValue());      //返还比例

        Wallet wallet = null;                       //钱包对象
        DirectPrize directPrize = null;             //直推实体对象

        Integer num = null;                         //接收受影响的行数

        // 4、for循环 未返还完的继续返还
        for (DirectPrizeVo directPrizeVo : voList) {

            wallet = new Wallet();
            wallet.setUserId(directPrizeVo.getRefereeId());                       //推荐人编号
            wallet.setAmount(directPrizeVo.getAmount().multiply(deductionPrice)); //返还的数量
            //wallet.setCoinId(directPrizeVo.getCoinId());                          //币种

            directPrize = new DirectPrize();
            directPrize.setAmount(directPrizeVo.getAmount().multiply(deductionPrice));
            directPrize.setId(directPrizeVo.getId());

            try{

                // 5、查询推荐人钱包信息
                //Wallet userWallet = walletMapper.selectUserWalletByCoinId(wallet.getUserId(),wallet.getCoinId());
              //  if(null == userWallet){

             //       return ApiResponseResult.build(2010,"error","未查询到推荐人信息","");
             //   }
                // 6、查询被推荐人钱包信息
               // Wallet directWallet = walletMapper.selectUserWalletByCoinId(directPrizeVo.getCoverRefereeId(),directPrizeVo.getCoinId());
              //  if(null == directWallet){

            //        return ApiResponseResult.build(2010,"error","未查询到被推荐人信息","");
            //    }

                // 7、转出记录 钱包编号(id)、价格(amount)
                num = directPrizeMapper.modifyDirectPrizeInfo(directPrize);
                if(num == 0){

                    return ApiResponseResult.build(2010,"error","转出失败","");
                }

                // 8、转入记录 钱包编号(id)、价格(amount)
               // wallet.setId(userWallet.getId());                                       //钱包编号
                num = walletMapper.modifyWalletToChangeInto(wallet);                    //修改返还直推奖 给 推荐人
                if(num == 0){

                    return ApiResponseResult.build(2010,"error","转入失败","");
                }

                // 9、新增转出记录 userId(转出userId)、coinId(币种编号)、coinType(币种名称)、txType(直推 3)、from(转出地址)、to(转入地址)、hash(UUID)、txStatus(状态 1)、capital(转账完后的本金),createTime(新增时间)
               // directWallet.setAmount(directPrizeVo.getAmountAvailable());
               /// num = insertWalletTurnTo(userWallet,directWallet,wallet.getAmount());
                if(num == 0){

                    return ApiResponseResult.build(2010,"error","新增失败","");
                }

                // 10、新增转入记录 userId(转入userId)、coinId(币种编号)、coinType(币种名称)、txType(直推 3)、from(转出地址)、to(转入地址)、hash(UUID)、txStatus(状态 1)、capital(转账完后的本金),createTime(新增时间)
                //num = insertWalletToChargeTo(userWallet,directWallet,directPrize.getAmount());
                if(num == 0){

                    return ApiResponseResult.build(2010,"error","新增失败","");
                }

            }catch (Exception e){
                e.printStackTrace();
                logger.warning("用户编号:"+directPrizeVo.getCoverRefereeId()+",币种编号:"+directPrizeVo.getCoinId()+
                        ",返钱多少:"+wallet.getAmount()+",当前时间:"+sdf.format(new Date()));
            }
        }

        return ApiResponseResult.build(200,"success","今天任务返还完毕",num);
    }

    /**
     * 动态奖
     * @return
     * @throws Exception
     */
    @Transactional
    public ApiResponseResult dynamicAward()throws Exception{


        BigDecimal dynamicPrincipal = RewardConfigureUtils.getInstance().getDynamicPrincipal();             //10000 动态奖本金,
        BigDecimal dynamicRation = RewardConfigureUtils.getInstance().getDynamicRation();                   //0.05 动态奖奖励百分比,
        Integer outRecommendNumbers = RewardConfigureUtils.getInstance().getOutRecommendNumbers();          //3 出局制度推荐人数,
        BigDecimal outPrincipalLimint = RewardConfigureUtils.getInstance().getOutPrincipalLimint();         //10000 出局制本金限制
        BigDecimal outRecommendMultiple = RewardConfigureUtils.getInstance().getOutRecommendMultiple();     //5 出局有推荐人本金的倍数
        BigDecimal outMultiple = RewardConfigureUtils.getInstance().getOutMultiple();                       //2 出局无倍数奖励

        // 1、查询所有用户信息
        List<User> userList =  userMapper.findAllUsers(1,1,null);
        if(null == userList){

            return ApiResponseResult.build(2010,"error","未查询到用户信息","");
        }

        List<WalletVo> voList = new ArrayList<>();

        for(User user : userList){

            // 2、查询所有用户下的所有钱包币种(一个钱包 = 一个币种 )
            voList = walletMapper.selectUserWalletCoinList(user.getId(),null);
            if(null == voList){

                //return ApiResponseResult.build(2010,"error","未查询到钱包信息","");
                continue;
            }

            // 3、拿出(单个钱包=单个币种)本金 做判断




        }

        return null;
    }



    /**
     * 新增订单记录 (返还直推奖  转出)
     * @return
     */
    public Integer insertWalletTurnTo(Wallet userWallet,Wallet directWallet,BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(directWallet.getUserId());        //用户编号
        //transcation.setCoinId(directWallet.getCoinId());        //币种编号
        transcation.setCoinType(directWallet.getCoinName());    //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(3);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖）
        transcation.setFrom(directWallet.getAddress());         //转出地址
        transcation.setTo(userWallet.getAddress());             //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(directWallet.getAmount().subtract(deductionPrice));     //本金 - 返还的金额  百分之0.5%
        transcation.setRemark(ObjectUtils.getRemark(directWallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;
    }


    /**
     * 新增订单记录 (返还直推奖  转入)
     * @return
     */
    public Integer insertWalletToChargeTo(Wallet userWallet,Wallet directWallet,BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(userWallet.getUserId());          //用户编号
       // transcation.setCoinId(userWallet.getCoinId());          //币种编号
        //transcation.setCoinType(userWallet.getCoinName());      //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(3);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖）
        transcation.setFrom(directWallet.getAddress());         //转出地址
        transcation.setTo(userWallet.getAddress());             //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(userWallet.getAmount().add(deductionPrice));     //本金 + 返还的金额  百分之0.5%
        transcation.setRemark(ObjectUtils.getRemark(userWallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletToChangeInto(transcation);

        return num;
    }

    /**
     * 新增利息生息订单记录
     * @param userWallet
     * @param deductionPrice
     * @return
     * @throws Exception
     */
    public Integer insertWalletInterestToChargeTo(Wallet userWallet,BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(userWallet.getUserId());          //用户编号
       // transcation.setCoinId(userWallet.getCoinId());          //币种编号
        transcation.setCoinType(userWallet.getCoinName());      //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(4);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖）
        transcation.setFrom(userWallet.getAddress());           //转出地址
        transcation.setTo(userWallet.getAddress());             //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(userWallet.getAmount().add(deductionPrice));     //本金 + 返还的金额  百分之0.5%
        transcation.setRemark(ObjectUtils.getRemark(userWallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletToChangeInto(transcation);

        return num;
    }


    public static void main(String[] args) {

        BigDecimal staticGreaterRation = RewardConfigureUtils.getInstance().getStaticGreaterRation();       //0.5 静态本金大于百分比,

        BigDecimal sss = staticGreaterRation.divide(new BigDecimal("100"));

        System.out.println(sss);

    }


}
