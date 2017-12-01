package com.travelsky.ypb.domain.support;

/**
 * <p>Tilte:事件类型</p>
 * <p>Description:每个事件类型对应一个事件类</p>
 * @author huc
 */
public enum EventType {
    EVENT_TYPE_5501("5501","com.travelsky.ypb.process.Event5501"),
    EVENT_TYPE_5502("5502","com.travelsky.ypb.process.Event5502"),
    EVENT_TYPE_5507("5507","com.travelsky.ypb.process.Event5507"),
    EVENT_TYPE_5508("5508","com.travelsky.ypb.process.Event5508"),
    EVENT_TYPE_1024("1024","com.travelsky.ypb.process.Event1024");//抢票事件

    private String typeCode;//
    private String typeClazz;//

    private EventType(String typeCode,String typeClazz){
        this.typeCode = typeCode;
        this.typeClazz = typeClazz;
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
        return null;
    }
    
}
