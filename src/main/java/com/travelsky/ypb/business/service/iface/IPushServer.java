package com.travelsky.ypb.business.service.iface;

import com.travelsky.ypb.domain.message.Instance;

/**
 * Created by huc on 2017/11/26.
 * 消息推送接口
 */
public interface IPushServer {

    String push(Instance instance);

    String newToken(String appId);
}
