package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.vo.WalletStatusVo;
import pro.bechat.wallet.domain.model.vo.WalletVo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 钱包数据Mapper
 * create WalletMapper by huc
 * 2018/7/23  上午12:03
 */
@Repository
public interface WalletMapper {


    /**
     * 查询用户钱包币种列表数据
     * @param userId
     * @param id
     * @return
     * @
     */
    List<WalletVo> selectUserWalletCoinList(@Param("userId")Integer userId,@Param("id")Integer id);

    /**
     * 锁住钱包表
     * @
     */
    List<Wallet> lockWalletTable(@Param("id")Integer id);

    /**
     * 修改钱包资产 转入
     * @param wallet
     * @return
     * @
     */
    Integer modifyWalletToChangeInto(Wallet wallet);

    /**
     * 查询用户钱包下是否有该币种
     * @param userId 用户编号
     * @param id  钱包编号
     * @return
     * @
     */
    Wallet selectUserWalletByCoinId(@Param("userId")Integer userId,@Param("id")Integer id);

    /**
     * 新增用户钱包币种信息
     * @param list
     * @return
     * @
     */
    Integer insertUserWalletInfo(List<Wallet> list);

    /**
     * 查询钱包信息,冻结金额不为 0 的  冻结金额就是用来生息的,  如果3天未提取利息的不再生息
     * @return
     * @
     */
    List<Wallet> selectUserWalletInterest();

    /** 修改用户钱包币种 利息生息 **/
    Integer modifyUserWalletInterest(Wallet wallet);


    Integer insertWalletInfo(Wallet wallet) ;


    String findWalletAddressByUserId(@Param("userId") Integer userId, @Param("coinName") String coinName);


    Wallet findWalletByUserIdAndAddress(@Param("userId") Integer userId, @Param("contractAddr") String contractAddr);


    List<Wallet> findUserWalletInfo(@Param("userId") Integer userId);

    /** 查询用户下的币种,只读取币种名称 **/
    List<Wallet> findWalletListInfo(@Param("userId") Integer userId);

    /** 删除合约币信息 **/
    Integer deleteContractAddrInfo(Wallet wallet);



}
