package pro.bechat.wallet.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.bechat.wallet.domain.dao.*;
import pro.bechat.wallet.domain.model.model.*;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.*;
import pro.bechat.wallet.domain.service.WalletService;
import pro.bechat.wallet.publics.ObjectUtils;
import pro.bechat.wallet.publics.PageBean;
import java.math.BigDecimal;
import java.util.*;


/**
 * 钱包业务Service实现
 * create WalletMapper by huc
 * 2018/7/23  上午12:03
 */
@Service
public class WalletServiceImpl implements WalletService {


    @Autowired
    private WalletMapper walletMapper;                      //钱包数据Mapper

    @Autowired
    private TranscationMapper transcationMapper;            //交易订单Mapper

    @Autowired
    private UserMapper userMapper;                          //用户信息Mapper

    @Autowired
    private CoinMapper coinMapper;                          //币种信息Mapper

    @Autowired
    private DictionaryMapper dictionaryMapper;              //数据字典Mapper

    @Autowired
    private DirectPrizeMapper directPrizeMapper;            //直推奖记录Mapper

    @Autowired
    private InvestmentMapper investmentMapper;              //投资(福利)Mapper


    @Override
    public ApiResponseResult selectUserWalletTotal(Integer userId) throws Exception {

        User user = userMapper.findUserById(userId);

        if(null == user){

            return ApiResponseResult.build(2015,"error","该用户不存在","");
        }

        BigDecimal total =  walletMapper.selectUserWalletTotal(userId);

        if(null == total){

            total = new BigDecimal(0);              //如果用户不存在 或者 没有交易记录就为 0
        }

        return ApiResponseResult.build(200,"success","查询用户钱包币种总额",total);
    }

    @Override
    public ApiResponseResult selectUserWalletCoinList(Integer currentPage,Integer currentSize,
                                                      Integer userId,String coinName) throws Exception {

        PageHelper.startPage(currentPage,currentSize);

        List<WalletVo> voList =  walletMapper.selectUserWalletCoinList(currentPage,currentSize,userId,coinName);

        if(null == voList){

            return ApiResponseResult.build(2011,"error","未查询用户钱包币种列表数据","");
        }

        PageInfo<WalletVo> pageInfo = new PageInfo<>(voList);
        PageBean<WalletVo> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentSize(currentSize);
        pageBean.setTotalNum(pageInfo.getTotal());
        pageBean.setItems(voList);

        return ApiResponseResult.build(200,"success","查询用户钱包币种列表数据",pageBean);
    }

    @Transactional
    @Override
    public ApiResponseResult modifyWalletTurnOut(Wallet wallet) throws Exception {

        // 1、锁钱包表
        //锁表出错，建议使用事务，不建议锁表。
        walletMapper.lockWalletTable();

        // 2、查询该币种信息,拿出金额做判断
        Wallet userWalletCoin = walletMapper.selectUserWalletCoinById(wallet);      //按id查询查询转出币种信息
        if(userWalletCoin == null){

            return ApiResponseResult.build(2010,"error","未查询到用户钱包下的币种","");
        }

        Wallet walletCoin = walletMapper.selectUserWalletCoinByAddress(wallet);     //按address查询转入币种信息
        if(walletCoin == null){
            return ApiResponseResult.build(2010,"error","未查询到该地址信息","");
        }

        if(userWalletCoin.getAddress().equals(walletCoin.getAddress())){

            return ApiResponseResult.build(2010,"error","不允许同一个地址发生交易","");
        }

        if(userWalletCoin.getCoinId() != walletCoin.getCoinId()){

            return ApiResponseResult.build(2010,"error","不能跨币种转账,只能同币交易","");
        }

        //查询数据字典表,查询出相对于的手续费用 id 为 5,为 提现
        List<DictionaryVo> voList = dictionaryMapper.selectDictionaryListById(5,"DEDUCT_FORMALITIES");

        if(null == voList){

            return ApiResponseResult.build(2010,"error","未查询到数据字典内容信息","");
        }

        //将 手续费率 拿出来扣除
        DictionaryVo dictionaryVo = voList.get(0);
        BigDecimal deductionPrice = userWalletCoin.getAvailableAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        userWalletCoin.setAvailableAmount(userWalletCoin.getAvailableAmount().subtract(deductionPrice));

        // 3、判断钱包币种的 本金价格 与传入的价格比较 可用余额与 传入价格比较
        // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
        int compare = userWalletCoin.getAvailableAmount().compareTo(wallet.getAmount());

        if(compare == 0 || compare == -1 ){

            return ApiResponseResult.build(2011,"error","币种数量不足","");
        }

        // 4、修改用户钱包币种数量(转出) (本金-转出金额) 和 (可用余额-转出金额)
        Integer num = walletMapper.modifyWalletTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","转出失败","");
        }

        // 5、修改用户钱包币种数量(转入) (本金+转出金额) 和 (可用余额+转出金额)  walletCoin转入对象
        wallet.setId(walletCoin.getId());
        num = walletMapper.modifyWalletToChangeInto(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","转入失败","");
        }

        // 6、增加1条用户转出记录
        num = insertWalletTurnTo(wallet,userWalletCoin);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","新增转出失败","");
        }

        // 7、增加1条用户转入记录
        num = insertWalletToChangeInto(wallet,walletCoin,userWalletCoin);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","新增转入失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 8、增加一条扣除手续费用的记录
        num = insertWalletDeductionTurnTo(wallet,userWalletCoin,deductionPrice);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
        }

        // 9、扣除可用余额
        wallet.setId(userWalletCoin.getId());
        wallet.setAmount(deductionPrice);
        num = walletMapper.modifyWalletTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用失败","");
        }

        return ApiResponseResult.build(200,"success","提币成功",num);
    }

    @Override
    public ApiResponseResult selectUserWalletCoinStraightOrInterest(Integer currentPage,Integer currentSize,
                                                                    Integer userId) throws Exception {

        PageHelper.startPage(currentPage,currentSize);

        Map<String,Object> map = new HashMap<>();

        List<WalletVo> voList = walletMapper.selectUserWalletCoinList(currentPage,currentSize,userId,null);

        if(null == voList){

            return ApiResponseResult.build(2013,"error","未钱包管理币种列表信息直推和利息","");
        }

        PageInfo<WalletVo> pageInfo = new PageInfo<>(voList);

        List<WalletCoinVo>  walletCoinVoList = new ArrayList<>();

        WalletCoinVo walletCoinVo = null;

        //遍历  拿出 币种名称(coinType) 去查询  直推  和  利息
        for(WalletVo walletVo : voList){

            walletCoinVo = new WalletCoinVo();

            map = transcationMapper.selectUserWalletCoinStraightOrInterest(userId,walletVo.getCoinName());

            walletCoinVo.setAddress(walletVo.getAddress());
            walletCoinVo.setId(walletVo.getId());
            walletCoinVo.setCoinId(walletVo.getCoinId());
            walletCoinVo.setCoinImg(walletVo.getCoinImg());
            walletCoinVo.setCoinName(walletVo.getCoinName());
            walletCoinVo.setAmount(walletVo.getAmount());
            walletCoinVo.setFreeAmount(walletVo.getFreeAmount());

            // straightPush 直推奖总额  interest 利息生息总额
            if(map == null){

                walletCoinVo.setStraightPush(BigDecimal.valueOf(0));
                walletCoinVo.setInterest(BigDecimal.valueOf(0));
            }else{
                if(map.get("straightPush") != null){

                    walletCoinVo.setStraightPush(new BigDecimal(map.get("straightPush").toString()));
                }
                if(map.get("interest") != null){

                    //walletCoinVo.setFreeAmount(walletVo.getFreeAmount().add(new BigDecimal(map.get("interest").toString())));
                    walletCoinVo.setInterest(new BigDecimal(map.get("interest").toString()));
                }
            }

            walletCoinVoList.add(walletCoinVo);
        }

        PageBean<WalletCoinVo> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentSize(currentSize);
        pageBean.setTotalNum(pageInfo.getTotal());
        pageBean.setItems(walletCoinVoList);

        return ApiResponseResult.build(200,"success","钱包管理币种列表信息直推和利息",pageBean);
    }




    @Override
    public ApiResponseResult selectYesterdayProfit(Integer userId, Integer coinId) throws Exception {

        Map<String,Object> map = new HashMap<>();

        Wallet wallet = walletMapper.selectUserWalletByCoinId(userId,coinId);

        if(null == wallet){

            return ApiResponseResult.build(2013,"error","未查询到有该币种","");
        }

        map = walletMapper.selectYesterdayProfit(userId,coinId);

        List<Map<String,Object>>  list = new ArrayList<>();
        list.add(map);

        return ApiResponseResult.build(200,"success","管理钱包用户币种昨日收益",list);
    }

    @Transactional
    @Override
    public ApiResponseResult modifyWalletDepositToChangeInto(Wallet wallet) throws Exception {

        // 1、锁钱包表
        walletMapper.lockWalletTable();

        // 2、拿到钱包币种信息  按编号去查询
        Wallet userWalletCoin = walletMapper.selectUserWalletCoinById(wallet);      //按id查询查询转出币种信息
        if(userWalletCoin == null){

            return ApiResponseResult.build(2010,"error","未查询到用户钱包下的币种","");
        }

        //查询数据字典表,查询出相对于的手续费用 id 为 2,为 转入
        List<DictionaryVo> voList = dictionaryMapper.selectDictionaryListById(2,"DEDUCT_FORMALITIES");

        if(null == voList){

            return ApiResponseResult.build(2010,"error","未查询到数据字典内容信息","");
        }

        //将 手续费率 拿出来扣除
        DictionaryVo dictionaryVo = voList.get(0);
        BigDecimal deductionPrice = userWalletCoin.getAvailableAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        userWalletCoin.setAvailableAmount(userWalletCoin.getAvailableAmount().subtract(deductionPrice));

        // 3、可用余额与转入价格比较
        // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
        int compare = userWalletCoin.getAvailableAmount().compareTo(wallet.getAmount());

        if(compare == 0 || compare == -1 ){

            return ApiResponseResult.build(2012,"error","币种数量不足","");
        }

        // 4、修改表数据 冻结数据加,可用减少
        Integer num = walletMapper.modifyWalletDepositToChangeInto(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","修改失败","");
        }

        // 5、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)增加 可用金额(availableAmount)减少 )
        num = insertWalletDepositTurnTo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","增加失败","");
        }

        // 6、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)减少 可用金额(availableAmount)增加 )
        num = insertWalletDepositToChangeInfo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","增加失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 7、增加一条扣除手续费用的记录
        num = insertWalletDepositToChangeInfoOrTurnTo(userWalletCoin,wallet,deductionPrice);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
        }

        // 8、扣除可用余额
        wallet.setId(userWalletCoin.getId());
        wallet.setAmount(deductionPrice);
        num = walletMapper.modifyWalletTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用失败","");
        }

        return ApiResponseResult.build(200,"success","转入成功",num);
    }

    @Transactional
    @Override
    public ApiResponseResult modifyWalletDepositTurnOut(Wallet wallet) throws Exception {

        // 1、锁钱包表
        walletMapper.lockWalletTable();

        // 2、拿到钱包币种信息  按编号去查询
        Wallet userWalletCoin = walletMapper.selectUserWalletCoinById(wallet);      //按id查询查询转出币种信息
        if(userWalletCoin == null){

            return ApiResponseResult.build(2010,"error","未查询到用户钱包下的币种","");
        }

        //查询数据字典表,查询出相对于的手续费用 id 为 2,为 转入
        List<DictionaryVo> voList = dictionaryMapper.selectDictionaryListById(2,"DEDUCT_FORMALITIES");

        if(null == voList){

            return ApiResponseResult.build(2010,"error","未查询到数据字典内容信息","");
        }

        //将 手续费率 拿出来扣除
        DictionaryVo dictionaryVo = voList.get(0);
        BigDecimal deductionPrice = userWalletCoin.getAvailableAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        userWalletCoin.setAvailableAmount(userWalletCoin.getAvailableAmount().subtract(deductionPrice));

        // 3、冻结金额与转入价格比较
        // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
        int compare = userWalletCoin.getFreeAmount().compareTo(wallet.getAmount());
        if(compare == 0 || compare == -1 ){

            return ApiResponseResult.build(2011,"error","币种数量不足","");
        }

        // 4、修改表数据 冻结数据减少,可用余额增加
        Integer num = walletMapper.modifyWalletDepositTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","修改失败","");
        }

        // 5、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)减少 可用金额(availableAmount)增加 )
        num = insertWalletDepositTurnTo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2013,"error","增加失败","");
        }

        // 6、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)增加 可用金额(availableAmount)减少 )
        num = insertWalletDepositToChangeInfo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2014,"error","增加失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 7、增加一条扣除手续费用的记录
        num = insertWalletDepositToChangeInfoOrTurnTo(userWalletCoin,wallet,deductionPrice);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
        }

        // 8、扣除可用余额
        wallet.setId(userWalletCoin.getId());
        wallet.setAmount(deductionPrice);
        num = walletMapper.modifyWalletTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用失败","");
        }

        return ApiResponseResult.build(200,"success","转出成功",num);
    }

    @Override
    public Integer insertUserWalletInfo(Integer userId) throws Exception {

        List<CoinVo>  voList = coinMapper.selectCoinList();
        if(null == voList){

            return 0;
        }

        Wallet wallet = null;                   //钱包对象
        Investment investment = null;           //投资对象

        List<Wallet>  walletList = new ArrayList<>();
        List<Investment> investmentList = new ArrayList<>();

        for(CoinVo coinVo : voList){

            wallet = new Wallet();

            wallet.setCoinId(coinVo.getId());
            wallet.setAddress(ObjectUtils.getUUID());
            wallet.setPrivateKey(ObjectUtils.getUUID());
            wallet.setPasswd(ObjectUtils.getUUID());
            wallet.setKeystore(ObjectUtils.getUUID());
            wallet.setUserId(userId);

            walletList.add(wallet);
        }

        Integer num = walletMapper.insertUserWalletInfo(walletList);

        for(Wallet wallet1 : walletList){

            investment = new Investment();

            investment.setWalletId(wallet1.getId());

            investmentList.add(investment);
        }

        num = investmentMapper.insertUserInvestmentInfo(investmentList);

        return num;

    }

    @Transactional
    @Override
    public ApiResponseResult modifyChargeMoneyInfo(Wallet wallet) throws Exception {

        // 1、锁住钱包表
        walletMapper.lockWalletTable();

        // 1、按照id去查询钱包信息
        Wallet userWalletCoin = walletMapper.selectUserWalletCoinById(wallet);      //按id查询查询转出币种信息
        if(userWalletCoin == null){

            return ApiResponseResult.build(2010,"error","未查询到该用户钱包","");
        }

        // 2、查询是否有交易记录 如果有交易记录 就执行正常,执行在钱包增加钱就行了 , 如果没记录,就说明是第一次充币,新增直推奖信息记录
        List<TranscationVo> transcationVoList =  transcationMapper.selectUserCoinTransactionList(1,1,
                userWalletCoin.getUserId(),wallet.getCoinName());

        // 没有记录,证明是第一次,1、查询推荐人 2、计算金额 本金*30%
        if(null == transcationVoList || transcationVoList.size() == 0){

            // 查询推荐人
            // 3、按照 钱包信息中userId 去查询 用户 信息, 被推荐人,判断 被推荐人是否 null or "" ,如果是执行正常操作,不是 就判断是否有交易记录
            User user = userMapper.findUserById(userWalletCoin.getUserId());
            if(user == null){

                return ApiResponseResult.build(2010,"error","未查询到该用户信息","");
            }

            String referee = "";      //推荐人

            // 判断推荐人是否存在, 推荐人不存在
            if(null != user.getRelationship() && !user.getRelationship().equals("0")){

                String [] str = user.getRelationship().split(",");

                referee = str[str.length-1];          //拿到最后一位,为推荐人

                //新增直推奖记录信息
                DirectPrize directPrize = new DirectPrize();
                directPrize.setRefereeId(Integer.parseInt(referee));            //推荐人
                directPrize.setCoverRefereeId(userWalletCoin.getUserId());      //被推荐人
                directPrize.setCoinId(userWalletCoin.getCoinId());              //币种编号
                String proportion = String.valueOf(30f/100f);                   //拿到百分之30的推荐奖的比例
                directPrize.setAmount(wallet.getAmount().multiply(new BigDecimal(proportion)));  //推荐奖30%
                directPrize.setAmountAvailable(directPrize.getAmount());        //可用余额

                //查询用户直推奖记录用户不能超过3条
                Integer count =  directPrizeMapper.selectDirectPrizeCount(Integer.parseInt(referee));

                if(count < 3){
                    Integer num = directPrizeMapper.insertDirectPrizeInfo(directPrize);

                    if(num == 0){

                        return ApiResponseResult.build(2012,"error","新增失败","");
                    }
                }
            }
        }

        //执行正常 钱包充币操作
        //查询数据字典表,查询出相对于的手续费用 id 为 4,为 充值
        List<DictionaryVo> voList = dictionaryMapper.selectDictionaryListById(4,"DEDUCT_FORMALITIES");

        if(null == voList){

            return ApiResponseResult.build(2012,"error","未查询到数据字典内容信息","");
        }

        //将 手续费率 拿出来扣除
        DictionaryVo dictionaryVo = voList.get(0);
        BigDecimal deductionPrice = userWalletCoin.getAvailableAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        userWalletCoin.setAvailableAmount(userWalletCoin.getAvailableAmount().subtract(deductionPrice));

        // 3、判断钱包币种的 本金价格 与传入的价格比较 可用余额与 传入价格比较
        // 结果 : 1 表示 大于; 0 表示 等于; -1 表示 小于
        /*int compare = userWalletCoin.getAvailableAmount().compareTo(wallet.getAmount());

        if(compare == 0 || compare == -1 ){

            return ApiResponseResult.build(2011,"error","币种数量不足","");
        }*/

        // 4、执行用户充值操作
        Integer num = walletMapper.modifyWalletToChangeInto(wallet);

        if(num == 0){

            return ApiResponseResult.build(2012,"error","充值失败","");
        }

        // 5、增加充值的订单记录
         num = insertWalletToChangeInto(wallet,userWalletCoin,userWalletCoin);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","新增充值记录失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 6、增加一条扣除手续费用的记录
        num = insertWalletRechargeTurnTo(wallet,userWalletCoin,deductionPrice);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
        }

        // 7、扣除可用余额
        wallet.setId(userWalletCoin.getId());
        wallet.setAmount(deductionPrice);
        num = walletMapper.modifyWalletTurnOut(wallet);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","扣除费用失败","");
        }

        return ApiResponseResult.build(200,"success","充值成功",num);
    }



    /**
     * 用户充值扣除手续费用
     * @param wallet
     * @param userWalletCoin
     * @param deductionPrice
     * @return
     * @throws Exception
     */
    public Integer insertWalletRechargeTurnTo(Wallet wallet,Wallet userWalletCoin,BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(userWalletCoin.getUserId());      //用户编号
        transcation.setCoinId(userWalletCoin.getCoinId());      //币种编号
        transcation.setCoinType(wallet.getCoinName());          //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(6);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖，6:扣除手续费）
        transcation.setFrom(userWalletCoin.getAddress());       //转出地址
        transcation.setTo(wallet.getAddress());                 //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(userWalletCoin.getAmount().add(wallet.getAmount()).subtract(deductionPrice));     //本金
        transcation.setRemark(ObjectUtils.getWalletRemark(wallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;
    }


    /**
     * 扣除提币手续费用的订单记录
     * @return
     */
    public Integer insertWalletDeductionTurnTo(Wallet wallet,Wallet userWalletCoin,BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(userWalletCoin.getUserId());      //用户编号
        transcation.setCoinId(userWalletCoin.getCoinId());      //币种编号
        transcation.setCoinType(wallet.getCoinName());          //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(6);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖，6:扣除手续费）
        transcation.setFrom(userWalletCoin.getAddress());       //转出地址
        transcation.setTo(wallet.getAddress());                 //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(userWalletCoin.getAmount().subtract(wallet.getAmount()).subtract(deductionPrice));     //本金
        transcation.setRemark(ObjectUtils.getWalletRemark(wallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;
    }


    /**
     * 新增订单记录 (钱包提币到交易所)
     * @return
     */
    public Integer insertWalletTurnTo(Wallet wallet,Wallet userWalletCoin)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(userWalletCoin.getUserId());      //用户编号
        transcation.setCoinId(userWalletCoin.getCoinId());      //币种编号
        transcation.setCoinType(wallet.getCoinName());          //币种名称
        transcation.setAmount(wallet.getAmount());              //交易金额
        transcation.setTxType(8);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）
        transcation.setFrom(userWalletCoin.getAddress());       //转出地址
        transcation.setTo(wallet.getAddress());                 //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(userWalletCoin.getAmount().subtract(wallet.getAmount()));     //本金
        transcation.setRemark(ObjectUtils.getWalletRemark(wallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;
    }

    /**
     * 新增订单记录 (交易所转入到钱包) 充值
     * @return
     */
    public Integer insertWalletToChangeInto(Wallet wallet,Wallet walletCoin,Wallet userWalletCoin)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(walletCoin.getUserId());          //用户编号
        transcation.setCoinId(userWalletCoin.getCoinId());      //币种编号
        transcation.setCoinType(wallet.getCoinName());          //币种名称
        transcation.setAmount(wallet.getAmount());              //交易金额
        transcation.setTxType(7);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）
        transcation.setFrom(userWalletCoin.getAddress());       //转出地址
        transcation.setTo(wallet.getAddress());                 //转入地址
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(walletCoin.getAmount().add(wallet.getAmount()));         //本金
        transcation.setRemark(ObjectUtils.getWalletRemark(wallet.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletToChangeInto(transcation);

        return num;
    }


    /**
     * 新增订单记录 (钱包管理 转出 钱包 为可用数量)
     * @param wallet 钱包对象
     * @return
     * @throws Exception
     */
    public Integer insertWalletDepositTurnTo(Wallet wallet,Wallet walletBean)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(wallet.getUserId());              //用户编号
        transcation.setCoinId(wallet.getCoinId());              //币种编号
        transcation.setCoinType(walletBean.getCoinName());      //币种名称
        transcation.setAmount(walletBean.getAmount());          //交易金额
        transcation.setTxType(2);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）
        transcation.setFrom(wallet.getAddress());               //转出地址 from
        transcation.setTo(wallet.getAddress());                 //转入地址 to
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(wallet.getAmount());             //本金不变
        transcation.setRemark(ObjectUtils.getRemark(walletBean.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;

    }

    /**
     * 新增订单记录 (钱包 转入 钱包管理 为冻结数量)
     * @param wallet
     * @return
     * @throws Exception
     */
    public Integer insertWalletDepositToChangeInfo(Wallet wallet,Wallet walletBean)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(wallet.getUserId());              //用户编号
        transcation.setCoinId(wallet.getCoinId());              //币种编号
        transcation.setCoinType(walletBean.getCoinName());      //币种名称
        transcation.setAmount(walletBean.getAmount());          //交易金额
        transcation.setTxType(1);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）
        transcation.setFrom(wallet.getAddress());               //转出地址 from
        transcation.setTo(wallet.getAddress());                 //转入地址 to
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(wallet.getAmount());             //本金不变
        transcation.setRemark(ObjectUtils.getRemark(walletBean.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletToChangeInto(transcation);

        return num;
    }

    /**
     * 新增订单记录 (钱包 转入 钱包管理 为冻结数量)
     * @param wallet
     * @return
     * @throws Exception
     */
    public Integer insertWalletDepositToChangeInfoOrTurnTo(Wallet wallet,Wallet walletBean,
                                                           BigDecimal deductionPrice)throws Exception{

        Transcation transcation = new Transcation();
        transcation.setUserId(wallet.getUserId());              //用户编号
        transcation.setCoinId(wallet.getCoinId());              //币种编号
        transcation.setCoinType(walletBean.getCoinName());      //币种名称
        transcation.setAmount(deductionPrice);                  //交易金额
        transcation.setTxType(6);                               //交易类型（1：转入，2：转出，3：直推，4：利息，5:团队奖(动态奖)，6:扣除手续费，7、充值 8、提现）
        transcation.setFrom(wallet.getAddress());               //转出地址 from
        transcation.setTo(wallet.getAddress());                 //转入地址 to
        transcation.setHash(ObjectUtils.getUUID());             //交易ID
        transcation.setTxStatus(1);                             //交易状态（1：已提交，2：已完成）
        transcation.setCapital(wallet.getAmount().subtract(deductionPrice));             //本金不变
        transcation.setRemark(ObjectUtils.getRemark(walletBean.getRemark(),transcation.getTxType())); //备注

        Integer num = transcationMapper.insertWalletTurnOut(transcation);

        return num;
    }


}
