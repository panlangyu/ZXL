package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.Investment;
import java.util.List;

/**
 * 投资(福利)Mapper
 */
@Repository
public interface InvestmentMapper {


    /**
     * 新增投资(福利) 信息
     * @param investment
     * @return
     * @
     */
    Integer insertUserInvestmentInfo(List<Investment> investment);

    /**
     * 查询投资信息单条信息,按钱包币种编号
     * @param walletId
     * @return
     * @
     */
    Investment selectInvestmentByWalletId(@Param("walletId")Integer walletId);

    /**
     * 更新  利息奖、直推奖、动态奖
     * @param investment
     * @return
     * @
     */
    Integer modifyInvestmentInfo(Investment investment);

}
