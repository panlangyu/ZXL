package pro.bechat.wallet.domain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.bechat.wallet.domain.dao.*;
import pro.bechat.wallet.domain.model.model.*;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.*;
import pro.bechat.wallet.domain.service.WalletService;
import pro.bechat.wallet.publics.HttpUtils;
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

    @Value("${yellowduck.url}")
    private String url ;                                    //第三方服务器端口


    @Override
    public ApiResponseResult selectUserWalletTotal(Integer userId) throws Exception {

        User user = userMapper.findUserById(userId);

        if(null == user){

            return ApiResponseResult.build(2015,"error","用户不存在","");
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

        //List<WalletVo> voList =  walletMapper.selectUserWalletCoinList(currentPage,currentSize,userId,coinName);
        List<WalletVo> voList =  new ArrayList<>();

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

/*        if(userWalletCoin.getAddress().equals(walletCoin.getAddress())){

            return ApiResponseResult.build(2010,"error","不允许同一个地址发生交易","");
        }

        if(userWalletCoin.getCoinId() != walletCoin.getCoinId()){

            return ApiResponseResult.build(2010,"error","不能跨币种转账,只能同币交易","");
        }*/

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
        //num = insertWalletTurnTo(wallet,userWalletCoin);
        //if(num == 0){

        //    return ApiResponseResult.build(2012,"error","新增转出失败","");
        //}

        // 7、增加1条用户转入记录
       // num = insertWalletToChangeInto(wallet,walletCoin,userWalletCoin);
       // if(num == 0){

       //     return ApiResponseResult.build(2012,"error","新增转入失败","");
       // }

        //计算出可用余额
       // deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 8、增加一条扣除手续费用的记录
       // num = insertWalletDeductionTurnTo(wallet,userWalletCoin,deductionPrice);
       // if(num == 0){

       //     return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
       // }

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

        //List<WalletVo> voList = walletMapper.selectUserWalletCoinList(currentPage,currentSize,userId,null);
        List<WalletVo> voList =  new ArrayList<>();

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
            //walletCoinVo.setCoinId(walletVo.getCoinId());
            walletCoinVo.setCoinImg(walletVo.getCoinImg());
            walletCoinVo.setCoinName(walletVo.getCoinName());
            //walletCoinVo.setAmount(walletVo.getAmount());
           // walletCoinVo.setFreeAmount(walletVo.getFreeAmount());

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

        Wallet wallet = walletMapper.selectUserWalletByCoinId(userId,"");

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
        //num = insertWalletDepositTurnTo(userWalletCoin,wallet);
        //if(num == 0){

        //    return ApiResponseResult.build(2012,"error","增加失败","");
       // }

        // 6、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)减少 可用金额(availableAmount)增加 )
       // num = insertWalletDepositToChangeInfo(userWalletCoin,wallet);
     //   if(num == 0){

       //     return ApiResponseResult.build(2012,"error","增加失败","");
       // }

        //计算出可用余额
        //deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 7、增加一条扣除手续费用的记录
        //num = insertWalletDepositToChangeInfoOrTurnTo(userWalletCoin,wallet,deductionPrice);
        //if(num == 0){

       //     return ApiResponseResult.build(2012,"error","扣除费用记录失败","");
       // }

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
        //num = insertWalletDepositTurnTo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2013,"error","增加失败","");
        }

        // 6、增加订单记录 ( 钱包 转出 到 钱包管理 冻结金额(freeAmount)增加 可用金额(availableAmount)减少 )
       // num = insertWalletDepositToChangeInfo(userWalletCoin,wallet);
        if(num == 0){

            return ApiResponseResult.build(2014,"error","增加失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 7、增加一条扣除手续费用的记录
       // num = insertWalletDepositToChangeInfoOrTurnTo(userWalletCoin,wallet,deductionPrice);
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

            //wallet.setCoinId(coinVo.getId());
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
                //directPrize.setCoinId(userWalletCoin.getCoinId());              //币种编号
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
        // num = insertWalletToChangeInto(wallet,userWalletCoin,userWalletCoin);
        if(num == 0){

            return ApiResponseResult.build(2012,"error","新增充值记录失败","");
        }

        //计算出可用余额
        deductionPrice = wallet.getAmount().multiply(new BigDecimal(dictionaryVo.getValue()));      //手续费用=(可用余额*扣费率)

        // 6、增加一条扣除手续费用的记录
        //num = insertWalletRechargeTurnTo(wallet,userWalletCoin,deductionPrice);
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


    @Override
    public ApiResponseResult createWalletInfo(UserWalletVo user) throws Exception {

        User userInfo = userMapper.findUserExist(user.getPhone());
        if (null == userInfo) {

            return ApiResponseResult.build(2010, "error", "用户不存在", "");
        }

        String addressExits = walletMapper.findWalletAddressByUserId(userInfo.getId(), "ETH");
        if (addressExits != null && !addressExits.equals("")) {

            return ApiResponseResult.build(2013, "error", "您已添加过该币种", "");
        }

        String uri = "";                //第三方API接口路径
        uri = url + "/register";

        Wallet wallet = new Wallet();
        wallet.setPasswd(user.getPasswd());
        wallet.setUserId(userInfo.getId());

        Map<String, String> map = new HashMap();
        map.put("phone", user.getPhone());
        map.put("txpw", wallet.getPasswd());

        String json = JSONArray.toJSONString(map);

        String str = HttpUtils.sendPost(uri, json);
        if (str == null || str.equals("") || str.equals("null")) {

            return ApiResponseResult.build(2010, "error", "系统异常", "");
        }

        String address = ObjectUtils.getAddress(str);

        if(address == null || address.equals("")){

            return ApiResponseResult.build(2010, "error", "系统异常", "");
        }

        wallet.setAddress(address);                 //ETH地址
        wallet.setName("ETH");                      //币种全称
        wallet.setCoinName("ETH");                  //币种名称
        wallet.setPrivateKey(ObjectUtils.getUUID());
        wallet.setKeystore(ObjectUtils.getUUID());
        wallet.setCoinImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg");

        //新增钱包信息
        Integer num = walletMapper.insertWalletInfo(wallet);
        if (num == 0) {

            return ApiResponseResult.build(2010, "error", "开通钱包失败", "");
        }
        return ApiResponseResult.build(200, "success", "开通钱包成功", str);
    }

    @Override
    public ApiResponseResult findUserWalletList(Integer userId, String coinName) throws Exception {

        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            return ApiResponseResult.build(2010, "error", "用户不存在", "");
        }

        //PageHelper.startPage(currentPage, currentSize);

        List<WalletVo> voList = walletMapper.selectUserWalletCoinList(userInfo.getId(), coinName);
        if (null == voList) {

            return ApiResponseResult.build(2011, "error", "未查询到用户钱包币种信息", "");
        }

        //PageInfo<WalletVo> pageInfo = new PageInfo(voList);
        //PageBean<WalletVo> pageBean = new PageBean();
        //pageBean.setCurrentPage(currentPage);
        //pageBean.setCurrentSize(currentSize);
        //pageBean.setTotalNum(pageInfo.getTotal());
        //pageBean.setItems(voList);

        return ApiResponseResult.build(200, "success", "查询用户钱包币种列表数据", voList);
    }

    @Transactional
    @Override
    public ApiResponseResult modifyWithdrawMoney(WalletUtilsVo wallet) throws Exception {

        //查看当前用户是否存在
        User userInfo = userMapper.findUserById(wallet.getUserId());
        if (null == userInfo) {

            return ApiResponseResult.build(2011, "error", "用户不存在", "");
        }

        Wallet userWalletCoin = walletMapper.selectUserWalletByCoinId(userInfo.getId(), wallet.getCoinName());
        if (userWalletCoin == null) {

            return ApiResponseResult.build(2011, "error", "未查询到用户下的币种信息", "");
        }

        //验证密码输入是否正确
        if(userWalletCoin.getPasswd() != null && !userWalletCoin.getPasswd().equals("")){

            if( !userWalletCoin.getPasswd().equals(wallet.getPasswd())){

                return ApiResponseResult.build(2011, "error", "密码输入不正确", "");
            }
        }

        //当前用户转账锁钱包表
        walletMapper.lockWalletTable();

        int trun = new BigDecimal(wallet.getValue()).compareTo(BigDecimal.ZERO);
        if (trun == 0 || trun == -1) {

            return ApiResponseResult.build(2011, "error", "请输入大于 0 的正数", "");
        }

        String uri = "";                //第三方API接口路径

        Map<String, String> amountMap = new HashMap();
        Map<String, String> txMap = new HashMap();

        if (userWalletCoin.getContractAddr() != null && !userWalletCoin.getContractAddr().equals("")) {

            uri = url+ "/token/balance";
            amountMap.put("contractAddr", userWalletCoin.getContractAddr());
            amountMap.put("from", userWalletCoin.getAddress());
        } else {

            uri = url + "/address";
            amountMap.put("address", userWalletCoin.getAddress());
        }

        String str = "";
        BigDecimal price = new BigDecimal("0");

        str = HttpUtils.sendGet(uri, amountMap, (2));

        if(str == null || str.equals("") || str.equals("null")){

            return ApiResponseResult.build(2011, "error", "余额不足", "");
        }

        price = ObjectUtils.getPrice(str);

        //看是否出异常
        Integer compareZero = price.compareTo(BigDecimal.ZERO);
        if(compareZero == -1){

            return ApiResponseResult.build(2011, "error", "系统异常", "");
        }

        //拿出币种数量和转账数量比较
        int compare = price.compareTo(new BigDecimal(wallet.getValue()));
        if (compare == 0 || compare == -1) {
            return ApiResponseResult.build(2011, "error", "币种数量不足", "");
        }

        txMap.put("sign", userWalletCoin.getPasswd());
        txMap.put("to", wallet.getAddress());
        txMap.put("value", wallet.getValue());
        //txMap.put("gas", "6050");
        //txMap.put("gasPrice", "7810");
        if (userWalletCoin.getContractAddr() != null && !userWalletCoin.getContractAddr().equals("")) {

            uri = url + "/token/sendTx";

            txMap.put("contractAddr", userWalletCoin.getContractAddr());
            txMap.put("from", userWalletCoin.getAddress());
        }else {

            uri = url + "/sendTx";
            txMap.put("from", userWalletCoin.getAddress());
        }

        String json = JSONArray.toJSONString(txMap);
        str = HttpUtils.sendPost(uri, json);

        if (str == null || str.equals("") || str.equals("null")) {

            return ApiResponseResult.build(2011, "error", "提币失败", "");
        }

        String hash = ObjectUtils.getHash(str);         //拿出成功的hash

        if(hash != null && hash.equals("-1")){

            return ApiResponseResult.build(2011, "error", "旷工费不足", "");
        }
        if(hash == null || hash.equals("")){

            return ApiResponseResult.build(2011, "error", "系统异常", "");
        }

        //wallet.setHash(hash);                           //转账成功的hash值

        //新增转账记录
        //Integer num = insertWalletTurnTo(wallet, userWalletCoin);
        //if (num == 0) {
         //   return ApiResponseResult.build(2011, "error", "新增交易记录失败", "");
        //}
        return ApiResponseResult.build(200, "success", "提币成功","OK");
    }

    @Override
    public ApiResponseResult queryContractAddr(Integer userId, String contractAddr) throws Exception {

        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            return ApiResponseResult.build(2013, "error", "用户不存在", "");
        }

        Wallet walletByAddress = walletMapper.findWalletByUserIdAndAddress(userInfo.getId(), contractAddr);
        if (walletByAddress != null) {

            return ApiResponseResult.build(2013, "error", "您已添加过该币种", "");
        }

        List<Wallet> walletList = walletMapper.findUserWalletInfo(userInfo.getId());
        if (null == walletList || walletList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "该用户还未添加过钱包", "");
        }

        Wallet walletVo = walletList.get(0);

        String uri = "";                //第三方API接口路径

        uri = url + "/token";

        Map<String, String> map = new HashMap();
        map.put("contractAddr", contractAddr);

        String str = HttpUtils.sendGet(uri, map, 2);
        if (str == null || str.equals("") || str.equals("null")) {

            return ApiResponseResult.build(2012, "error", "系统异常", "");
        }

        String coinName = ObjectUtils.getNum(str);

        if(coinName.equals("-1")){

            return ApiResponseResult.build(2012, "error", "币种不存在", "");
        }
        if(coinName.equals("-2")){

            return ApiResponseResult.build(2012, "error", "系统异常", "");
        }

        Wallet wallet = new Wallet();

        wallet.setCoinName(coinName);
        wallet.setUserId(userInfo.getId());
        wallet.setPrivateKey(ObjectUtils.getUUID());
        wallet.setKeystore(ObjectUtils.getUUID());
        wallet.setCoinImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535186178127&di=99253031ea26fe3f96df0f091f0dfd09&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D764621767%2C3821218275%26fm%3D214%26gp%3D0.jpg");
        wallet.setPasswd(walletVo.getPasswd());
        wallet.setAddress(walletVo.getAddress());
        wallet.setContractAddr(contractAddr);

        //新增合约币信息
        Integer num = walletMapper.insertWalletInfo(wallet);
        if (num == 0) {

            return ApiResponseResult.build(2012, "error", "添加合约币信息失败", "");
        }

        return ApiResponseResult.build(200, "success", "添加合约币信息成功", wallet);
    }

    @Override
    public ApiResponseResult queryAccountList() throws Exception {

        String uri = "";                //第三方API接口路径
        uri = url + "/accounts";

        Map<String, String> map = new HashMap();
        String str = HttpUtils.sendGet(uri, map, 2);
        if ((str == null) || (str.equals(""))) {

            return ApiResponseResult.build(2011, "error", "未查询到账户数据", "");
        }
        return ApiResponseResult.build(200, "success", "查询所有钱包账户", str);
    }

    @Override
    public ApiResponseResult blockNumber() {

        String uri = "";                //第三方API接口路径
        uri = url + "/blockNumber";

        Map<String, String> map = new HashMap();

        String str = HttpUtils.sendGet(uri, map, (2));
        if (str == null || str.equals("")) {

            return ApiResponseResult.build(2011, "error", "未查询到阻塞数信息", "");
        }
        return ApiResponseResult.build(200, "success", "查询阻塞数信息", JSONArray.parseObject(str));
    }

    @Override
    public ApiResponseResult createContractWalletInfo(WalletContractVo walletContractVo) throws Exception {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(walletContractVo.getUserId());
        if (null == userInfo) {

            return ApiResponseResult.build(2013, "error", "用户不存在", "");
        }

        // 2、查询币种是否被该用户已添加
        Wallet userWallet = walletMapper.findWalletByUserIdAndAddress(walletContractVo.getUserId(), walletContractVo.getContractAddr());
        if (userWallet != null) {

            return ApiResponseResult.build(2013, "error", "已添加过该币种", "");
        }

        // 3、拿到第一个钱包，ETH(以太坊)
        List<Wallet> walletList = walletMapper.findUserWalletInfo(userInfo.getId());
        if (null == walletList || walletList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "用户还未添加过钱包", "");
        }

        Wallet walletVo = walletList.get(0);

        Wallet wallet = new Wallet();

        wallet.setName(walletContractVo.getName());                         //币种全称
        wallet.setCoinName(walletContractVo.getCoinName());                 //币种名称
        wallet.setUserId(walletContractVo.getUserId());                     //用户编号
        wallet.setPrivateKey(ObjectUtils.getUUID());
        wallet.setKeystore(ObjectUtils.getUUID());
        wallet.setCoinImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535107678063&di=3edb7ba93dad1f79bac072909cedc46d&imgtype=0&src=http%3A%2F%2Fcimg.pcstore.com.tw%2Fcprd%2FC1124031499_big.png%3Fpimg%3Dstatic%26P%3D1506092677");
        wallet.setPasswd(walletVo.getPasswd());                             //交易密码
        wallet.setAddress(walletVo.getAddress());                           //ETH地址
        wallet.setContractAddr(walletContractVo.getContractAddr());         //合约币地址

        //新增合约币信息
        Integer num = walletMapper.insertWalletInfo(wallet);
        if (num == 0) {

            return ApiResponseResult.build(2015, "error", "添加合约币信息失败", "");
        }

        return ApiResponseResult.build(200, "success", "添加合约币信息成功", num);
    }

    @Override
    public ApiResponseResult findUserWalletListStatus(Integer userId, String list) throws Exception {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            return ApiResponseResult.build(2013, "error", "用户不存在", "");
        }

        // 2、查询用户已拥有币种
        List<Wallet> statusVoList =  walletMapper.findWalletListInfo(userId);
        if (null == statusVoList || statusVoList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "该用户未拥有币种", "");
        }

        // 3、拿出参数值
        List<String> strList = (List<String>)JSONObject.parse(list);
        if(null == strList || strList.size() == 0){

            return ApiResponseResult.build(2013, "error", "该用户未拥有币种", "");
        }

        WalletStatusVo statusVo = null;

        List<WalletStatusVo> walletStatusVoList = new ArrayList<>();


        //拿出币种名称比较
        for(String string : strList){

            statusVo = new WalletStatusVo();

            statusVo.setContractAddr(string);
            statusVo.setStatus(false);

            for (Wallet wallet : statusVoList) {

                if(wallet.getContractAddr() != null && !wallet.getContractAddr().equals("")){

                    if(string.equals(wallet.getContractAddr())){

                        statusVo.setStatus(true);
                        break;
                    }
                }
            }

            walletStatusVoList.add(statusVo);
        }

        return ApiResponseResult.build(200, "success", "查询用户已拥有币种信息", walletStatusVoList);
    }

    @Override
    public ApiResponseResult deleteContractAddrInfo(Wallet wallet) throws Exception {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(wallet.getUserId());
        if (null == userInfo) {

            return ApiResponseResult.build(2013, "error", "用户不存在", "");
        }

        Integer num = walletMapper.deleteContractAddrInfo(wallet);
        if(num == 0){

            return ApiResponseResult.build(2013, "error", "删除失败", "");
        }

        return ApiResponseResult.build(200, "success", "删除成功", num);
    }



    /*@Override
    public ApiResponseResult queryUserWalletInfo(String phone, String earnerPhone) throws Exception {

        UserVo user = userMapper.findUserExist(phone);
        if (null == user) {

            return ApiResponseResult.build(2013, "error", "当前用户不存在", "");
        }
        UserVo earnerUser = userMapper.findUserExist(earnerPhone);
        if (null == earnerUser) {

            return ApiResponseResult.build(2013, "error", "被转账用户不存在", "");
        }
        List<Wallet> walletList = walletMapper.findUserWalletInfo(user.getId());
        if (walletList == null || walletList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "该用户没有钱包信息", "");
        }
        List<Wallet> earnerWalletList = walletMapper.findUserWalletInfo(earnerUser.getId());
        if (earnerWalletList == null || earnerWalletList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "被转账用户没有钱包信息", "");
        }

        List<WalletStatusVo> voList = new ArrayList();
        WalletStatusVo walletStatusVo = null;

        for (Wallet wallet : walletList) {
            walletStatusVo = new WalletStatusVo();
            for (Wallet wallet1 : earnerWalletList) {

                walletStatusVo.setCoinName(wallet.getCoinName());
                walletStatusVo.setStatus(false);

                if (wallet.getCoinName().equals(wallet1.getCoinName())) {

                    walletStatusVo.setStatus(true);
                    break;
                }
            }
            voList.add(walletStatusVo);
        }

        return ApiResponseResult.build(200, "success", "当前用户与转账用户的币种比较", voList);
    }*/

    /*@Override
    public ApiResponseResult findWalletListInfo(String phone) throws Exception {

        UserVo user = userMapper.findUserExist(phone);
        if (null == user) {

            return ApiResponseResult.build(2013, "error", "当前用户不存在", "");
        }

        List<WalletStatusVo> voList = walletMapper.findWalletListInfo(user.getId());
        if (null == voList || voList.size() == 0) {

            return ApiResponseResult.build(2013, "error", "该用户未拥有币种信息", "");
        }

        return ApiResponseResult.build(200, "success", "用户币种列表", voList);
    }*/


    public static void main(String[] args) {

        String str = "{\"body\":\"/eth/data/keystore/UTC--2018-08-25T05-11-38.246056000Z--56d2327cf864f8ba697b8c2c3e9306ba0771de06.json (No such file or directory)\",\"code\":1,\"type\":\"error\"}";

        String hash = "";

        JSONObject json = JSONObject.parseObject(str);




        if(json.getString("type").equals("error")){

            hash = "";
            System.out.println("123");
        }

        JSONObject body = (JSONObject)json.get("body");

        if(body.toString() != null && !body.toString().equals("") && !body.toString().equals("null")){

            hash = body.getString("address");
            System.out.println("123111");
        }






    }


}
