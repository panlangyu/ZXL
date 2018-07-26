package pro.bechat.wallet.domain.service;

import org.apache.ibatis.annotations.Param;
import pro.bechat.wallet.domain.model.response.ApiResponse;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.TranscationVo;

import java.util.List;

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
    public ApiResponseResult selectUserCoinTransactionList(Integer userId, String coinType)throws Exception;

    /**
     * 查询用户钱包下币种详情订单记录
     * @param userId 用户编号
     * @param startTime 查询月份
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectUserCoinTransactionListInfo(Integer userId
                                                        ,String startTime)throws Exception;

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
     * @param userId
     * @param coinType
     * @return
     * @throws Exception
     */
    public ApiResponseResult selectWalletUserCoinTransactionList(Integer userId,
                                                                 String coinType)throws Exception;



}
