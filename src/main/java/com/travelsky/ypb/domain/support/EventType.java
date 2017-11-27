package com.travelsky.ypb.domain.support;

/**
 * <p>Tilte:事件类型</p>
 * <p>Description:每个事件类型对应一个事件类</p>
 * @author huc
 */
public enum EventType {
	UNDEFINED("0000","com.travelsky.domain.ypb.EventBody","process"),
    EVENT_TYPE_5501("5501","com.travelsky.ypb.process.Event5501","process"),
    EVENT_TYPE_5502("5502","com.travelsky.ypb.process.Event5502","process"),
    EVENT_TYPE_5507("5507","com.travelsky.ypb.process.Event5507","process"),
    EVENT_TYPE_1024("1024","com.travelsky.ypb.process.Event1024","process"),//抢票事件
    EVENT_TYPE_5508("5508","com.travelsky.ypb.process.Event5508","process");

    private String typeCode;//5501, 5502
    private String typeClazz;//类
    private String initMethod;

    private EventType(String typeCode,String typeClazz,String initMethod){
        this.typeCode = typeCode;
        this.typeClazz = typeClazz;
        this.initMethod = initMethod;
    }

    private EventType(String typeCode){
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeClazz() {
        return typeClazz;
    }

    public void setTypeClazz(String typeClazz) {
        this.typeClazz = typeClazz;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    /**
     * @param typeCode
     */
    public static EventType fromTypeCode(String typeCode) {
        if (typeCode != null) {
            for (EventType b : EventType.values()) {
                if (typeCode.equalsIgnoreCase(b.typeCode)) {
                    return b;
                }
            }
        }
        return EventType.UNDEFINED;
    }
    
}
