package com.travelsky.ypb.domain.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelsky.ypb.domain.support.EventResponse;
import com.travelsky.ypb.domain.support.EventType;


/**
 * Created by huc on 2017/11/22.
 *
 */
public class InitMessageBody {

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(InitMessageBody.class);

    private InitMessageBody() {
    }

    public static final String suffix = "##";

    public static EventResponse init(final String msg){
        logger.info(msg);
        return proces(msg);
    }

    public static EventResponse proces(final String msg){
        EventResponse response = new EventResponse();

        String[] body = msg.split(suffix);
        String eventType = body[1];
        String eventTitle = body[2];
        String eventTimeStamp = body[3];
        String eventId = body[4];
        String json = body[7];

        Instance instance = JSONObject.toJavaObject(JSON.parseObject(json).getJSONObject("instance"), Instance.class);
        response.setEventType(EventType.fromTypeCode(eventType));
        response.setEventTitle(eventTitle);
        response.setEventTimeStamp(eventTimeStamp);
        response.setEventId(eventId);
        instance.setEventType(eventType);
        instance.setTitle(eventTitle);
        instance.setOriginalMessage(msg);
        response.setEventBody(instance);

        response.setClazz(EventType.fromTypeCode(eventType).getTypeClazz());
        return response;
    }

}
