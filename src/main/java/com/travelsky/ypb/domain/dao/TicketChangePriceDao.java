package com.travelsky.ypb.domain.dao;

import com.travelsky.ypb.domain.model.TicketChangePrice;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by huc on 2017/12/5.
 */
@SuppressWarnings("ALL")
@Repository
public class TicketChangePriceDao extends BaseDao<TicketChangePrice> {

    @Override
    protected String getNameSpace() {
        return "com.travelsky.ypb.domain.model.TicketChangePrice.";
    }

}
