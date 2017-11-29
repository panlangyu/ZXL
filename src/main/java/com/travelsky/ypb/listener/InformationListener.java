package com.travelsky.ypb.listener;

import com.travelsky.ypb.business.dynamicProxy.CglibDynamicProxy;
import com.travelsky.ypb.business.dynamicProxy.DynamicProxy;
import com.travelsky.ypb.domain.message.InitMessageBody;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.support.EventResponse;
import com.travelsky.ypb.domain.support.EventType;
import com.travelsky.ypb.domain.support.ServiceSupport;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huc on 2017/11/13.
 */
@Component
public class InformationListener {

    @Value("${activeMq:unknown}")
    public String queue;

    @Autowired
    ApplicationContext app;

    @JmsListener(destination = "UME_YPB_QUEUE")
    public void receiveMessages(Message message) {
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
        try {
            // TODO 初始化消息体
            EventResponse init = InitMessageBody.init(msg.getText());
            // TODO 任分发
            ((ServiceSupport)app.getBean(Class.forName(init.getClazz()))).process(init.getEventBody());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
