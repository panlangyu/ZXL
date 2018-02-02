package com.travelsky.ypb.business.service.impl;

import com.travelsky.ypb.business.service.iface.IPushServer;
import com.travelsky.ypb.business.task.CacheManager;
import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import com.travelsky.ypb.domain.service.MessagesBodyService;
import com.travelsky.ypb.publics.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by huc on 2017/11/26.
 * 消息推送
 */
@Service
public  class PushServiceImpl implements IPushServer<Instance> {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private HttpClient http;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    MessagesBodyService messagesBodyService;

    @Override
    public String push(Instance instance) {
        //TODO 获取token
        instance.setToken(newToken(instance.getAppid()[0]));
        Log.i(this.getClass(),"pushing",instance.getToken());
        //TODO 发送消息
        org.apache.commons.httpclient.HttpClient client = http.getHttpClient();
        HttpMethod httpPost;
        String result = "";
        try {
            httpPost = http.postMethod(appConfig.getPushUrl(), instance.getValue());
            client.executeMethod(httpPost);
            result = httpPost.getResponseBodyAsString();
            // 保存信息内容
            messagesBodyService.saveMessages(instance);
        } catch (IOException e) {
            Log.i(this.getClass(),"",e);
        }
        Log.i(this.getClass(),"publish",result);
        return result;
    }


    @Override
    public String newToken(String appId) {

        return (String) cacheManager.getCacheToken(appId).getValue();
    }


}
