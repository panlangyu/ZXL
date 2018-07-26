package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.vo.WalletVo;
import java.util.List;
import java.util.Map;

/**
 * 钱包数据Mapper
 * create WalletMapper by huc
 * 2018/7/23  上午12:03
 */
@Repository
public interface WalletMapper extends BasicMapper<User>{

    /**
     * 查询个人钱包总资产
     * @param userId
     * @return
     * @throws Exception
     */
    public Double selectUserWalletTotal(@Param("userId")Integer userId)throws Exception;

    /**
     * 查询用户钱包币种总个数
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer selectUserWalletCoinTotal(@Param("userId")Integer userId)throws Exception;

    /**
     * 查询用户钱包币种列表数据
     * @param userId
     * @return
     * @throws Exception
     */
    public List<WalletVo> selectUserWalletCoinList(@Param("userId")Integer userId
                                                , @Param("coinName")String coinName)throws Exception;

    /**
     * 查询用户钱包币种列表数据
     * @param userId
     * @return
     * @throws Exception
     */
    public List<WalletVo> selectUserWalletCoinList(@Param("currentPage")Integer currentPage,
                                                   @Param("currentSize")Integer currentSize,
                                                   @Param("userId")Integer userId,
                                                   @Param("coinName")String coinName)throws Exception;

    /**
     * 锁住钱包表
     * @throws Exception
     */
    public List<Wallet> lockWalletTable()throws Exception;

    /**
     * 查询钱包用户单个币种信息(按id查询)
     * @param wallet
     * @return
     * @throws Exception
     */
    public Wallet selectUserWalletCoinById(Wallet wallet)throws Exception;

    /**
     * 查询钱包用户单个币种信息(按地址查询)
     * @param wallet
     * @return
     * @throws Exception
     */
    public Wallet selectUserWalletCoinByAddress(Wallet wallet)throws Exception;

    /**
     * 修改钱包资产 转出
     * @param wallet
     * @return
     * @throws Exception
     */
    public Integer modifyWalletTurnOut(Wallet wallet)throws Exception;

    /**
     * 修改钱包资产 转入
     * @param wallet
     * @return
     * @throws Exception
     */
    public Integer modifyWalletToChangeInto(Wallet wallet)throws Exception;


    /**
     * 管理钱包用户币种昨日收益 +(冻结数量)
     * @param userId
     * @param coinId
     * @return
     * @throws Exception
     */
    public Map<String,Object> selectYesterdayProfit(@Param("userId")Integer userId,
                                                    @Param("coinId")Integer coinId)throws Exception;

}
