package pro.bechat.wallet.domain.service;

import io.swagger.annotations.Api;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.UserWalletVo;
import pro.bechat.wallet.domain.model.vo.WalletContractVo;
import pro.bechat.wallet.domain.model.vo.WalletUtilsVo;

/**
 * 钱包业务Service
 * create WalletMapper by huc
 * 2018/7/23  上午12:03
 */
public interface WalletService {


    /**
     * 查询个人钱包总资产
     * @param userId 用户编号
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserWalletTotal(Integer userId)throws Exception;

    /**
     * 查询用户钱包数据
     * @param currentPage 当前页码
     * @param currentSize 页面容量
     * @param userId  用户编号
     * @param coinName 币种名称
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserWalletCoinList(Integer currentPage,Integer currentSize,
                                                      Integer userId,String coinName)throws Exception;

    /**
     * 钱包转账(交易所对接)
     * @param wallet 钱包对象
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyWalletTurnOut(Wallet wallet)throws Exception;

    /**
     * 查询钱包管理币种的 直推 和 生息的总额
     * @param currentPage 当前页码
     * @param currentSize 页面容量
     * @param userId 用户编号
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserWalletCoinStraightOrInterest(Integer currentPage,
                                                                    Integer currentSize,
                                                                    Integer userId)throws Exception;


    /**
     * 管理钱包用户币种昨日收益 +(冻结数量)
     * @param userId 用户编号
     * @param coinId 币种编号
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectYesterdayProfit(Integer userId,Integer coinId)throws Exception;

    /**
     * 钱包管理转入(钱包数量 转入 钱包管理)
     * @param wallet 钱包对象
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyWalletDepositToChangeInto(Wallet wallet)throws Exception;

    /**
     * 钱包管理转出(从 钱包管理 转出到 钱包 同一币种做处理)
     * @param wallet 钱包对象
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyWalletDepositTurnOut(Wallet wallet)throws Exception;


    /**
     * 新增用户钱包币种信息
     * @param userId 用户编号
     * @return
     * @throws Exception
     */
    public Integer insertUserWalletInfo(Integer userId)throws Exception;


    /**
     * 用户充币操作
     * @param wallet
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyChargeMoneyInfo(Wallet wallet)throws Exception;


    /**
     * 创建钱包 ETH
     * @param user
     * @return
     * @throws Exception
     */
    public ApiResponseResult createWalletInfo(UserWalletVo user) throws Exception;

    /**
     * 查询钱包列表
     * @param userId
     * @param id
     * @return
     * @throws Exception
     */
    public ApiResponseResult findUserWalletList(Integer userId, Integer id) throws Exception;

    /**
     * 钱包转账
     * @param walletUtilsVo
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyWithdrawMoney(WalletUtilsVo walletUtilsVo) throws Exception;

    public ApiResponseResult queryContractAddr(Integer userId, String contractAddr) throws Exception;

    public ApiResponseResult queryAccountList() throws Exception;

    public ApiResponseResult blockNumber();

    /**
     * 新增合约币信息
     * @param walletContractVo
     * @return
     * @throws Exception
     */
    public ApiResponseResult createContractWalletInfo(WalletContractVo walletContractVo)throws Exception;

    /**
     * 用户已拥有币种
     * @param userId
     * @param list
     * @return
     * @throws Exception
     */
    public ApiResponseResult findUserWalletListStatus(Integer userId,String list)throws Exception;

    /**
     * 删除合约币信息
     * @param wallet
     * @return
     * @throws Exception
     */
    public ApiResponseResult deleteContractAddrInfo(Wallet wallet)throws Exception;


    //public ApiResponseResult queryUserWalletInfo(String paramString1, String paramString2)
            //throws Exception;

    /**
     * 查询用户下的币种,只读取币种名称
     * @param phone
     * @return
     * @throws Exception
     */
    //public ApiResponseResult findWalletListInfo(String phone)throws Exception;



}
