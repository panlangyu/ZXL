package com.travelsky.ypb.domain.dao;

import com.travelsky.ypb.domain.model.FlightPlan;
import org.springframework.stereotype.Repository;

/**
 * create FlightPlanDao by huc
 * 2017/12/7  下午4:19
 */
@Repository
public class FlightPlanDao extends BaseDao<FlightPlan>{

    @Override
    protected String getNameSpace() {
        return "com.travelsky.ypb.domain.model.FlightPlan.";
    }
}
