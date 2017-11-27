package com.travelsky.ypb.domain.service.flightplan;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.domain.dao.flightplan.FlightStatusSubScDao;
import com.travelsky.ypb.domain.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huc on 2017/11/23.
 */
@Service
public class LightStatusSubScService extends BaseService<LightStatusSubScService> {

    @Autowired
    private FlightStatusSubScDao dao;

    @Override
    protected BaseDao getDao() {
        return dao;
    }


}
