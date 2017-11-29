package com.travelsky.ypb.business.service.impl;

import com.travelsky.ypb.business.service.iface.IPushServer;
import com.travelsky.ypb.business.task.CacheManager;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.domain.message.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by huc on 2017/11/26.
 * 消息推送
 */
@Service
public  class PushServiceImpl implements IPushServer {

    private static final Logger log = LoggerFactory.getLogger("PushServer");

    @Override
    public String push(Instance instance) {
        //TODO 获取token
        instance.setToken(newToken(instance.getAppid()));
        Log.i(this.getClass(),"push",instance.getToken());
        //TODO 发送消息


        return null;
    }

    @Override
    public String newToken(String appId) {
        return (String) CacheManager.getCacheToken(appId).getValue();
    }


    public static void main(String[] args) throws InterruptedException {

        System.out.println(CacheManager.getCacheInfo("hello"));
        CacheManager.putCacheInfo("hello","World",CacheManager.TOKEN_TIME_OUT);
        System.out.println(CacheManager.getCacheInfo("hello"));

        Thread.sleep(2000);
        System.out.println(CacheManager.getCacheInfo("hello"));
    }


}
