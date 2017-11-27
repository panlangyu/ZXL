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
    ApplicationContext applicationContext;

    @JmsListener(destination = "UME_YPB_QUEUE")
    public void receiveMessages(Message message) {
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
        try {
            // 初始化消息体
            EventResponse init = InitMessageBody.init(msg.getText());
            // TODO 通过反射实现
            /*Method process = Class.forName(init.getClazz()).getMethod(init.getEventType().getInitMethod(), new Class[]{Instance.class});
            process.invoke(Class.forName(init.getClazz()).newInstance(),init.getEventBody());*/
            // TODO 通过动态代理实现
            /*((ServiceSupport) new DynamicProxy().newProxyInstance(Class.forName(init.getClazz()).newInstance()))
                    .process(init.getEventBody());*/
            ServiceSupport support = (ServiceSupport)applicationContext.getBean(Class.forName(init.getClazz()));
            support.process(init.getEventBody());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void main(String[] args)  {
        try{
            Class<?> aClass = Class.forName(EventType.EVENT_TYPE_5501.getTypeClazz());
            // TODO JDK 动态代理
            ServiceSupport invoke = (ServiceSupport) new DynamicProxy().newProxyInstance(aClass.newInstance());
            invoke.process(new Instance());
            // TODO Cglib 动态代理
            ServiceSupport process = (ServiceSupport) new CglibDynamicProxy().newProxyInstance(aClass);
            process.process(new Instance());
            Map<String,String> map = new HashMap<String,String>();
            map.put("1","1");
            String s = map.get("1");
            System.out.println(s);
            System.out.println(12%16);

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }


}
