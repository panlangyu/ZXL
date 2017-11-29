package com.travelsky.ypb.configuration;

import com.travelsky.ypb.rmi.client.core.RmiRegisterClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huc on 2017/11/27.
 */
@Configuration
public class BeanContext {

    @Value("${zookeeper.connection:unknown}")
    public String connection;

    @Value("${zookeeper.session.timeout:unknown}")
    public int timeout;

    @Value("${zookeeper.node.name:unknown}")
    public String nodeName;

    @Bean
    public RmiRegisterClient rmiRegisterClient(){
        RmiRegisterClient client = new RmiRegisterClient();
        client.setZkConnection(connection);
        client.setZkSessionTimeout(timeout);
        client.setZkNodeName(nodeName);
        return  client;
    }

}
