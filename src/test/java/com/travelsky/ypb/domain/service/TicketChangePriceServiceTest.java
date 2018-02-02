package com.travelsky.ypb.domain.service;

import com.travelsky.ypb.MessagesApplication;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 * Created by huc on 2017/12/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessagesApplication.class)
public class TicketChangePriceServiceTest {

    @Autowired
    TicketChangePriceService priceService;

    @Test
    public void testPrepareForIncrease()
            throws Exception {
        TicketChangePrice price = new TicketChangePrice();

        priceService.prepareForIncrease(price);
    }

    @Test
    public void testPrepareForReduction() throws Exception {

    }

}