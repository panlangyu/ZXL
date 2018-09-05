package pro.bechat.wallet.domain.dao;

import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.vo.CoinVo;
import java.util.List;

/**
 * 币种数据Mapper
 * create WalletMapper by huc
 * 2018/7/28 下午15:24
 */
@Repository
public interface CoinMapper {

    /**
     * 查询所有币种信息
     * @return
     * @throws Exception
     */
    List<CoinVo> selectCoinList();

}
