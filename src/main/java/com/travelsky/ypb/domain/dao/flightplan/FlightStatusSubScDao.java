package com.travelsky.ypb.domain.dao.flightplan;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.domain.model.FlightStatusSubSc;
import org.springframework.stereotype.Repository;

/**
 * Created by huc on 2017/11/23.
 */
@Repository
public class FlightStatusSubScDao extends BaseDao<FlightStatusSubSc> {

    @Override
    protected String getNameSpace() {
        return "com.travelsky.ypb.domain.model.FlightStatusSubSc.";
    }
}
