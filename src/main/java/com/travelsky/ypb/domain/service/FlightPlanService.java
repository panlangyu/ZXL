package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.domain.dao.FlightPlanDao;
import com.travelsky.ypb.domain.model.FlightPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create FlightPlanService by huc
 * 2017/12/7  下午4:21
 */
@Service
public class FlightPlanService extends BaseService<FlightPlan> {

    @Autowired
    protected FlightPlanDao dao;

    @Override
    protected BaseDao getDao() {
        return dao;
    }
}
