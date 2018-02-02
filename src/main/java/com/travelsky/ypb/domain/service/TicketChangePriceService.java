package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.domain.dao.BaseDao;
import com.travelsky.ypb.domain.dao.TicketChangePriceDao;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huc on 2017/12/5.
 */
@SuppressWarnings("ALL")
@Service
public class TicketChangePriceService extends BaseService<TicketChangePrice> {

    @Autowired
    private TicketChangePriceDao dao;

    @Override
    protected BaseDao getDao() {

        return dao;
    }


    /**
     * 判断涨价
     * @param ticketChangePrice price
     * @return -1:降价 0:价格不变 1:涨价
     */
    public int prepareForIncrease(TicketChangePrice ticketChangePrice){
        List<TicketChangePrice> list = findList(ticketChangePrice);
        if (list.isEmpty()) return 0;
        TicketChangePrice price = list.get(0);
        return Integer.compare(Integer.parseInt(ticketChangePrice.getThisPrice())
                , Integer.parseInt(price.getThisPrice()));
    }

    public TicketChangePrice prepare(TicketChangePrice ticketChangePrice){
        List<TicketChangePrice> list = findList(ticketChangePrice);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public void update(Instance instance){
        TicketChangePrice ticketChangePrice = new TicketChangePrice();
        ticketChangePrice.setDept(instance.getDepartureAirport());
        ticketChangePrice.setDest(instance.getArrivalAirport());
        ticketChangePrice.setFlightNo(instance.getFlightNo());
        ticketChangePrice.setFlightDate(instance.getFlightDate());

        this.update(ticketChangePrice);
    }


}
