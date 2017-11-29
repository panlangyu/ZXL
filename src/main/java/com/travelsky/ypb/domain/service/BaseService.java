package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.domain.dao.BaseDao;

import java.util.List;

/**
 * Created by huc on 2017/11/23.
 */
public abstract class BaseService <T extends Object>{

    protected abstract BaseDao getDao();

    public void save(T t) {
        getDao().save(t);
    }

    public void update(T t) {
        getDao().update(t);
    }

    public T findById(long id) {
        return (T) getDao().findById(id);
    }

    public Long findCount(T t) {
        return getDao().findCount(t);
    }

    public List<T> findList(T t) {
        return getDao().findList(t);
    }

    public void delete(long id) {
        getDao().delete(id);
    }

    public void delete(T t) {
        getDao().delete(t);
    }
}