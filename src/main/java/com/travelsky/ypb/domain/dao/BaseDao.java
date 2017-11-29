package com.travelsky.ypb.domain.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by huc on 2017/11/23.
 */
public abstract class BaseDao <T extends Object>{

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    @Qualifier("sqlSessionTemplate")
    SqlSessionTemplate sqlSessionTemplate;

    protected abstract String getNameSpace();

    public void save(T t) {
        sqlSessionTemplate.insert(this.getNameSpace() + "save", t);
    }

    public void update(T t) {
        sqlSessionTemplate.update(this.getNameSpace() + "update", t);
    }

    public T findById(long id) {
        return sqlSessionTemplate.selectOne(getNameSpace() + "findById",id);
    }

    public Long findCount(T t) {
        return sqlSessionTemplate.selectOne(getNameSpace() + "findCount",t);
    }

    public List<T> findList(T t) {
        return sqlSessionTemplate.selectList(getNameSpace() + "findList",t);
    }

    public void delete(long id) {
        sqlSessionTemplate.delete(getNameSpace() + "delete", id);
    }

    public void delete(T t) {
        sqlSessionTemplate.delete(getNameSpace() + "delete", t);
    }

}
