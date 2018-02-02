package com.travelsky.ypb.domain.support;


import com.travelsky.ypb.domain.message.Instance;

/**
 * <p>Tilte:事件体对象</p>
 * <p>Description:</p>
 * @author huc
 */
@SuppressWarnings("ALL")
public class EventResponse {
	
	private EventType eventType;
	
	private String eventTitle;
	
	private String eventTimeStamp;
	
	private String eventId;
	
	private Instance eventBody;

	private String clazz;

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventTimeStamp() {
		return eventTimeStamp;
	}

	public void setEventTimeStamp(String eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Instance getEventBody() {
		return eventBody;
	}

	public void setEventBody(Instance eventBody) {
		this.eventBody = eventBody;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public EventResponse copy(){
		EventResponse eventResponse = new EventResponse();
		eventResponse.setEventBody(this.eventBody);
		eventResponse.setEventId(this.eventId);
		eventResponse.setEventTimeStamp(eventTimeStamp);
		eventResponse.setEventTitle(eventTitle);
		eventResponse.setEventType(eventType);
		return eventResponse;
	}

	@Override
	public String toString() {
		return "EventResponse{" +
				"eventType=" + eventType +
				", eventTitle='" + eventTitle + '\'' +
				", eventTimeStamp='" + eventTimeStamp + '\'' +
				", eventId='" + eventId + '\'' +
				", eventBody=" + eventBody +
				", clazz='" + clazz + '\'' +
				'}';
	}
}
