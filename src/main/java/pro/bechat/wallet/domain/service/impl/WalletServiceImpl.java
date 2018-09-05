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
import pro.bechat.wallet.domain.enums.UserEnum;
import pro.bechat.wallet.domain.enums.WalletEnum;
import pro.bechat.wallet.domain.exception.UserException;
import pro.bechat.wallet.domain.exception.WalletException;
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
    private UserMapper userMapper;                          //用户信息Mapper

    @Autowired
    private CoinMapper coinMapper;                          //币种信息Mapper

    @Autowired
    private InvestmentMapper investmentMapper;              //投资(福利)Mapper

    @Value("${yellowduck.url}")
    private String url ;                                    //第三方服务器端口



    @Override
    public Integer insertUserWalletInfo(Integer userId)  {

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


    @Override
    public ApiResponseResult createWalletInfo(UserWalletVo user)  {

        User userInfo = userMapper.findUserExist(user.getPhone());
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        String addressExits = walletMapper.findWalletAddressByUserId(userInfo.getId(), "ETH");
        if (addressExits != null && !addressExits.equals("")) {

            throw new WalletException(WalletEnum.WALLET_EXISTENT_COINNAME);
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

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
        }

        String address = ObjectUtils.getAddress(str);

        if(address == null || address.equals("")){
            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
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

            throw new WalletException(WalletEnum.WALLET_NOT_OPEN_UP);
        }

        return ApiResponseResult.build(200, "success", "开通钱包成功", str);
    }

    @Override
    public ApiResponseResult findUserWalletList(Integer userId, Integer id)  {

        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        List<WalletVo> voList = walletMapper.selectUserWalletCoinList(userInfo.getId(), id);
        if (null == voList) {

            throw new WalletException(WalletEnum.WALLET_NOT_LIST_INFO);
        }

        return ApiResponseResult.build(200, "success", "查询用户钱包币种列表数据", voList);
    }

    @Transactional
    @Override
    public ApiResponseResult modifyWithdrawMoney(WalletUtilsVo wallet)  {

        //查看当前用户是否存在
        User userInfo = userMapper.findUserById(wallet.getUserId());
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        Wallet userWalletCoin = walletMapper.selectUserWalletByCoinId(userInfo.getId(), wallet.getId());
        if (userWalletCoin == null) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_REPEAT);
        }

        //验证密码输入是否正确
        if(userWalletCoin.getPasswd() != null && !userWalletCoin.getPasswd().equals("")){

            if( !userWalletCoin.getPasswd().equals(wallet.getPasswd())){

                throw new WalletException(WalletEnum.WALLET_PASSWD_FAIL);
            }
        }

        /*  if(userWalletCoin.getAddress().equals(walletCoin.getAddress())){

            return ApiResponseResult.build(2010,"error","不允许同一个地址发生交易","");
        }

        if(userWalletCoin.getCoinId() != walletCoin.getCoinId()){

            return ApiResponseResult.build(2010,"error","不能跨币种转账,只能同币交易","");
        }*/


        //当前用户转账锁钱包表
        walletMapper.lockWalletTable(wallet.getId());

        int trun = new BigDecimal(wallet.getValue()).compareTo(BigDecimal.ZERO);
        if (trun == 0 || trun == -1) {

            throw new WalletException(WalletEnum.WALLET_NOT_GT_ZERO);
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

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
        }

        price = ObjectUtils.getPrice(str);

        //看是否出异常
        Integer compareZero = price.compareTo(BigDecimal.ZERO);
        if(compareZero == -1){

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
        }

        //拿出币种数量和转账数量比较
        int compare = price.compareTo(new BigDecimal(wallet.getValue()));
        if (compare == 0 || compare == -1) {

            throw new WalletException(WalletEnum.WALLET_AMOUNT_INSUFFICIENT);
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

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
        }

        String hash = ObjectUtils.getHash(str);         //拿出成功的hash

        if(hash != null && hash.equals("-1")){

            throw new WalletException(WalletEnum.WALLET_ABSENTEEISM_REPEAT);
        }
        if(hash == null || hash.equals("")){

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
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
    public ApiResponseResult queryContractAddr(Integer userId, String contractAddr)  {

        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        Wallet walletByAddress = walletMapper.findWalletByUserIdAndAddress(userInfo.getId(), contractAddr);
        if (walletByAddress != null) {

            throw new WalletException(WalletEnum.WALLET_EXISTENT_COINNAME);
        }

        List<Wallet> walletList = walletMapper.findUserWalletInfo(userInfo.getId());
        if (null == walletList || walletList.size() == 0) {

            throw new WalletException(WalletEnum.WALLET_NOT_LIST_INFO);
        }

        Wallet walletVo = walletList.get(0);

        String uri = "";                //第三方API接口路径

        uri = url + "/token";

        Map<String, String> map = new HashMap();
        map.put("contractAddr", contractAddr);

        String str = HttpUtils.sendGet(uri, map, 2);
        if (str == null || str.equals("") || str.equals("null")) {

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
        }

        String coinName = ObjectUtils.getNum(str);

        if(coinName.equals("-1")){

            throw new WalletException(WalletEnum.WALLET_NOT_EXISTENT_ERROR);
        }
        if(coinName.equals("-2")){

            throw new WalletException(WalletEnum.WALLET_SYSTEM_ERR);
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

            throw new WalletException(WalletEnum.WALLET_INSERT_COINNAME);
        }

        return ApiResponseResult.build(200, "success", "添加合约币信息成功", wallet);
    }

    @Override
    public ApiResponseResult queryAccountList()  {

        String uri = "";                //第三方API接口路径
        uri = url + "/accounts";

        Map<String, String> map = new HashMap();
        String str = HttpUtils.sendGet(uri, map, 2);

        if (str == null || str.equals("") || str.equals("null")) {

            throw new WalletException(WalletEnum.WALLET_NOT_ACCOUNT_INFO);
        }

        return ApiResponseResult.build(200, "success", "查询所有钱包账户", str);
    }

    @Override
    public ApiResponseResult blockNumber() {

        String uri = "";                //第三方API接口路径
        uri = url + "/blockNumber";

        Map<String, String> map = new HashMap();

        String str = HttpUtils.sendGet(uri, map, (2));
        if (str == null || str.equals("") || str.equals("null")) {

            throw new WalletException(WalletEnum.WALLET_NOT_BLOCK_INFO);
        }

        return ApiResponseResult.build(200, "success", "查询阻塞数信息", JSONArray.parseObject(str));
    }

    @Override
    public ApiResponseResult createContractWalletInfo(WalletContractVo walletContractVo)  {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(walletContractVo.getUserId());
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        // 2、查询币种是否被该用户已添加
        Wallet userWallet = walletMapper.findWalletByUserIdAndAddress(walletContractVo.getUserId(), walletContractVo.getContractAddr());
        if (userWallet != null) {

            throw new WalletException(WalletEnum.WALLET_EXISTENT_COINNAME);
        }

        // 3、拿到第一个钱包，ETH(以太坊)
        List<Wallet> walletList = walletMapper.findUserWalletInfo(userInfo.getId());
        if (null == walletList || walletList.size() == 0) {

            throw new WalletException(WalletEnum.WALLET_NOT_LIST_INFO);
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

            throw new WalletException(WalletEnum.WALLET_INSERT_COINNAME);
        }

        return ApiResponseResult.build(200, "success", "添加合约币信息成功", num);
    }

    @Override
    public ApiResponseResult findUserWalletListStatus(Integer userId, String list)  {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(userId);
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        // 2、查询用户已拥有币种
        List<Wallet> statusVoList =  walletMapper.findWalletListInfo(userId);
        if (null == statusVoList || statusVoList.size() == 0) {

            throw new WalletException(WalletEnum.WALLET_NOT_LIST_INFO);
        }

        // 3、拿出参数值
        List<String> strList = (List<String>)JSONObject.parse(list);
        if(null == strList || strList.size() == 0){

            throw new WalletException(WalletEnum.WALLET_NOT_MARKET);
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
    public ApiResponseResult deleteContractAddrInfo(Wallet wallet)  {

        // 1、查询用户是否存在
        User userInfo = userMapper.findUserById(wallet.getUserId());
        if (null == userInfo) {

            throw new WalletException(WalletEnum.WALLET_NOT_USER_INFO);
        }

        Integer num = walletMapper.deleteContractAddrInfo(wallet);
        if(num == 0){

            throw new WalletException(WalletEnum.WALLET_DELETE_FAIL);
        }

        return ApiResponseResult.build(200, "success", "删除成功", num);
    }



    /*@Override
    public ApiResponseResult queryUserWalletInfo(String phone, String earnerPhone)  {

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
    public ApiResponseResult findWalletListInfo(String phone)  {

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





}
