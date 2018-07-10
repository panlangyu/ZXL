package pro.bechat.wallet.domain.dao;

import java.util.List;

/**
 * create BasicMapper by huc
 * 2018/4/13  下午3:02
 */
public interface BasicMapper<T> {

    /**
     * 根据条件查询数据列表
     *
     * @param t
     * @return
     */
    List<T> findList(T t);

    /**
     * 根据主键删除数据
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 根据主键查询数据
     *
     * @param id
     * @return
     */
    T selectByPrimaryKey(String id);

    /**
     * 查询说有数据列表
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 更新数据
     *
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);


    int updateByPrimaryKeySelective(T t);

}
