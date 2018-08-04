package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.Dictionary;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.vo.DictionaryVo;
import java.util.List;

/**
 * 数据字典Mapper
 */
@Repository
public interface DictionaryMapper {


    /**
     * 查询数据字典,按分类查询,parentId不为0
     * @param type 类型名称
     * @return
     * @throws Exception
     */
    public List<Dictionary> selectDictionaryList(@Param("type")String type)throws Exception;

    /**
     * 查询数据字典,按分类查询,parentId不为0,也可以查询单条信息
     * @param id 编号
     * @param type 类型名称
     * @return
     * @throws Exception
     */
    public List<DictionaryVo> selectDictionaryListById(@Param("id")Integer id,
                                                       @Param("type")String type)throws Exception;




    int updateDictionaryValueById(@Param("id")int id,@Param("value")String value);



}
