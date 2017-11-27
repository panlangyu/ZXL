package com.travelsky.ypb.business.service.impl;

import com.travelsky.ypb.business.service.iface.IPushServer;
import com.travelsky.ypb.business.task.CacheManager;
import com.travelsky.ypb.domain.message.MessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by huc on 2017/11/26.
 * 消息推送
 */
@Service
public  class PushServiceImpl implements IPushServer {

    private static final Logger log = LoggerFactory.getLogger("PushServer");

    @Value("${appId:unknown}")
    public String appId;

    @Override
    public String push(MessageRequest messageRequest) {
        //TODO 获取token
        messageRequest.setToken(newToken(messageRequest.getAppid()));
        //TODO 发送消息


        return null;
    }

    @Override
    public String newToken(String appId) {
        return (String) CacheManager.getCacheInfo(appId).getValue();
    }
}
