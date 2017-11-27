package com.travelsky.ypb.business.service.iface;

import com.travelsky.ypb.domain.message.MessageRequest;

/**
 * Created by huc on 2017/11/26.
 * 消息推送接口
 */
public interface IPushServer {

    String push(MessageRequest messageRequest);

    String newToken(String appId);
}
