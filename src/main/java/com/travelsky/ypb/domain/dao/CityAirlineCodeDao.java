package com.travelsky.ypb.domain.dao;

import com.travelsky.ypb.domain.model.CityAirlineCode;
import org.springframework.stereotype.Repository;

/**
 * create CityAirlineCodeDao by huc
 * 2017/12/7  上午9:12
 */
@SuppressWarnings("ALL")
@Repository
public class CityAirlineCodeDao extends BaseDao<CityAirlineCode> {

    @Override
    protected String getNameSpace() {
        return "com.travelsky.ypb.domain.model.CityAirlineCode.";
    }


}
