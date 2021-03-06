package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.AdminTranscationBean;
import pro.bechat.wallet.domain.model.model.Transcation;
import pro.bechat.wallet.domain.model.vo.TranscationVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 币种订单交易Mapper
 */
@Repository
public interface TranscationMapper {

    /**
     * 查询用户钱包下币种详情订单记录
     * @param currentPage 当前页码
     * @param currentSize 页面容量
     * @param userId 用户编号
     * @param coinType 币种类型
     * @Param("startTime")String startTime,
     * @Param("endTime")String endTime
     * @return
     * @
     */
    List<TranscationVo> selectUserCoinTransactionList(@Param("currentPage")Integer currentPage, @Param("currentSize")Integer currentSize,
                                                      @Param("userId")Integer userId, @Param("coinType")String coinType);

    /**
     * 修改钱包资产 转出订单记录
     * @param transcation
     * @return
     * @
     */
    Integer insertWalletTurnOut(Transcation transcation);

    /**
     * 修改钱包资产 转入订单记录
     * @param transcation
     * @return
     * @
     */
    Integer insertWalletToChangeInto(Transcation transcation);

    /**
     * 查看币种交易记录总额,条件查询
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     * @
     */
    Double selectUserCoinTurnToTotal(@Param("userId")Integer userId, @Param("startTime")String startTime,
                                     @Param("endTime")String endTime);

    /**
     * 查看币种交易记录总额,条件查询
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     * @
     */
    Double selectUserCoinToChargeInfoTotal(@Param("userId")Integer userId, @Param("startTime")String startTime,
                                           @Param("endTime")String endTime);

    /**
     *  查看币种交易记录总额,条件查询
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     * @
     */
    Map<String,Object> selectUserCoinTrunToChargeTotal(@Param("userId")Integer userId, @Param("startTime")String startTime,
                                                       @Param("endTime")String endTime);

    /**
     * 查询钱包管理币种的 直推 和 生息的总额
     * @param userId
     * @param coinType
     * @return
     * @
     */
    Map<String,Object> selectUserWalletCoinStraightOrInterest(@Param("userId")Integer userId, @Param("coinType")String coinType);


    /**
     * 查询钱包管理币种的交易记录
     * @param currentPage
     * @param currentSize
     * @param userId
     * @param coinType
     * @param startTime
     * @param endTime
     * @return
     * @
     */
    List<TranscationVo> selectWalletUserCoinTransactionList(@Param("currentPage")Integer currentPage, @Param("currentSize")Integer currentSize,
                                                            @Param("userId")Integer userId, @Param("coinType")String coinType,
                                                            @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 查看充值记录 和 提取记录 总额 是否超过 10000
     * @param userId
     * @param coinId
     * @return
     * @
     */
    BigDecimal selectTranscationRechargeRecord(@Param("userId")Integer userId, @Param("coinId")Integer coinId);



    /**
     * 根据条件查询数据
     * @param params
     * @return
     */
    List<AdminTranscationBean> selectTranscationByParams(@Param("currentPage")Integer currentPage, @Param("currentSize")Integer currentSize,
                                                         @Param("params")Map<String,String> params);

}
