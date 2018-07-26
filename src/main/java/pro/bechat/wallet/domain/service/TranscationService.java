package pro.bechat.wallet.domain.service;

import pro.bechat.wallet.domain.model.response.ApiResponseResult;

/**
 * 用户币种交易订单Service
 */
public interface TranscationService {

    /**
     * 查询用户钱包下币种详情订单记录
     * @param userId 用户编号
     * @param coinType 币种分类
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserCoinTransactionList(Integer currentPage,Integer currentSize,
                                                           Integer userId, String coinType)throws Exception;

    /**
     * 查询用户钱包下币种详情订单记录
     * @param currentPage 当前页码
     * @param currentSize 显示个数
     * @param userId 用户编号
     * @param startTime 查询月份
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserCoinTransactionListInfo(Integer currentPage,
                                                               Integer currentSize,
                                                               Integer userId,
                                                               String startTime)throws Exception;

    /**
     * 查看币种交易记录总额,条件查询
     * @param userId
     * @param startTime
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserCoinTransactionTotal(Integer userId
                                                        ,String startTime)throws Exception;

    /**
     * 查看币种交易记录总额,条件查询 (收入和支出)
     * @param userId
     * @param startTime
     * @return
     * @throws Exception
     */
    public ApiResponseResult  selectUserCoinTrunToChargeTotal(Integer userId
            ,String startTime)throws Exception;


    /**
     * 查询钱包管理币种的交易记录
     * @param currentPage
     * @param currentSize
     * @param userId
     * @param coinType
     * @param startTime
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectWalletUserCoinTransactionList(Integer currentPage,
                                                                 Integer currentSize,
                                                                 Integer userId,
                                                                 String coinType,
                                                                 String startTime)throws Exception;


}
