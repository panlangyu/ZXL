package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.DirectPrize;
import pro.bechat.wallet.domain.model.vo.DirectPrizeVo;
import java.util.List;

/**
 * 用户直推人信息Mapper
 */
@Repository
public interface DirectPrizeMapper {


    /**
     * 查询是否用户直推的信息是否记录超过3条
     * @param refereeId 推荐人
     * @return
     * @
     */
    Integer selectDirectPrizeCount(@Param("refereeId")Integer refereeId);

    /**
     * 查询未返完的信息
     * @return
     * @
     */
    List<DirectPrizeVo> selectDirectPrizeList();

    /**
     * 新增直推奖记录
     * @param directPrize
     * @return
     * @
     */
    Integer insertDirectPrizeInfo(DirectPrize directPrize);

    /**
     * 修改 未返还的 直推奖
     * @param directPrize
     * @return
     * @
     */
    Integer modifyDirectPrizeInfo(DirectPrize directPrize);


}
