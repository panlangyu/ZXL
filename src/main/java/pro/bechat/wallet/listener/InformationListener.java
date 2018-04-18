package pro.bechat.wallet.listener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.jms.Message;

/**
 * MQ 信息监听
 * Created by huc on 2017/11/13.
 */
@SuppressWarnings("ALL")
@Component
public class InformationListener {

    Logger log = LogManager.getLogger(InformationListener.class);

    //@Value("${activeMq:unknown}")
    public String queue;

    @Autowired
    ApplicationContext app;

    //@JmsListener(destination = "UME_YPB_QUEUE",containerFactory = "jmsQueueListener")
    public void receiveMessages(Message message) {
        ActiveMQTextMessage msg = (ActiveMQTextMessage) message;

    }

}
