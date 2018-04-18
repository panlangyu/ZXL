package pro.bechat.wallet.domain.service;


import pro.bechat.wallet.domain.dao.BasicMapper;

import java.util.List;

abstract class BasicService<T> {

    protected abstract BasicMapper basicMapper();

    public void save(T t){

        basicMapper().insert(t);
    }

    public List<T> findList(T t){

        return basicMapper().findList(t);
    }

    public void update(T t){

        basicMapper().updateByPrimaryKey(t);
    }

    public int updateByPrimaryKeySelective(T t){

     return basicMapper().updateByPrimaryKeySelective(t);
    }

    public T selectByPrimaryKey(String l){

        return (T) basicMapper().selectByPrimaryKey(l);
    }

    public int deleteByPrimaryKey(Long l){

        return basicMapper().deleteByPrimaryKey(l);
    }

}