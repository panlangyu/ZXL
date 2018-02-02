package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.domain.dao.CityAirlineCodeDao;
import com.travelsky.ypb.domain.model.CityAirlineCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create CityAirlineCodeService by huc
 * 2017/12/7  上午9:13
 */
@SuppressWarnings("ALL")
@Service
public class CityAirlineCodeService extends BaseService<CityAirlineCode> {

    @Autowired
    protected CityAirlineCodeDao dao;

    @Override
    protected BaseDao getDao() {
        return dao;
    }
}
