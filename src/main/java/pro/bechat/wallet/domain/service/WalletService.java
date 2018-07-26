package pro.bechat.wallet.domain.service;

import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import java.util.Map;

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
     * @param userId  用户编号
     * @param coinName 币种名称
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserWalletCoinList(Integer userId,String coinName)throws Exception;

    /**
     * 钱包转账(交易所对接)
     * @param wallet
     * @return
     * @throws Exception
     */
    public ApiResponseResult modifyWalletTurnOut(Wallet wallet)throws Exception;


    /**
     * 查询钱包管理币种的 直推 和 生息的总额
     * @param userId
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserWalletCoinStraightOrInterest(Integer userId)throws Exception;


    /**
     * 用户钱包币种列表 分页
     * @param userId
     * @param currentPage
     * @param currentSize
     * @return
     */
    public ApiResponseResult selectUserWalletCoinStraig(Integer currentPage,
                                                        Integer currentSize,
                                                        Integer userId)throws Exception;


    /**
     * 管理钱包用户币种昨日收益 +(冻结数量)
     * @param userId
     * @param coinId
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectYesterdayProfit(Integer userId,
                                                    Integer coinId)throws Exception;




}
