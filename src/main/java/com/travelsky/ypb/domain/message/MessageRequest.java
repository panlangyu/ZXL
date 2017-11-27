package com.travelsky.ypb.domain.message;

import java.util.Map;

/**
 * <p>Tilte:消息模板对象</p>
 * <p>Description:</p>
 * @author huc
 */
public class MessageRequest {
	
	private String appid;
	
	private String token;
	
	//用于夜间消息保存 以单条航班或者是航线为唯一ID
	private String flightInfoId;
	
	//消息模板ID
	private long msgTempleId;
	
	//消息模板中对应的参数
	private Map<String, String> params;
	
	//跳转参数 可附带到手机端
	private Map<String, String> jumpParams;
	
	//推送的用户ID
	private String userId;
	
	//用于消息推送成功之后标记保存redis
	private String redisKey;
	
	private String changeRedisKey;
	
	private String changeContent;
	
	private double returnPoint;
	
	private double returnPrice;
	
	private String planid;
	
	private String eventType;
	private String eventTitle;
	private String flightDate;
	private String flightNo;
	private String departureAirport;
	private String arrivalAirport;
	private String airlineName;
	private String ticketCount;
	private int status;
	
	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getFlightInfoId() {
		return flightInfoId;
	}

	public void setFlightInfoId(String flightInfoId) {
		this.flightInfoId = flightInfoId;
	}

	public long getMsgTempleId() {
		return msgTempleId;
	}

	public void setMsgTempleId(long msgTempleId) {
		this.msgTempleId = msgTempleId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getJumpParams() {
		return jumpParams;
	}

	public void setJumpParams(Map<String, String> jumpParams) {
		this.jumpParams = jumpParams;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public String getChangeRedisKey() {
		return changeRedisKey;
	}

	public void setChangeRedisKey(String changeRedisKey) {
		this.changeRedisKey = changeRedisKey;
	}
	

	public String getChangeContent() {
		return changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	public double getReturnPoint() {
		return returnPoint;
	}

	public void setReturnPoint(double returnPoint) {
		this.returnPoint = returnPoint;
	}

	public double getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(double returnPrice) {
		this.returnPrice = returnPrice;
	}
	
	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String
				.format("flightInfoId=%s_msgTempleId=%s,_params=%s,_jumpParams=%s,_userId=%s,_redisKey=%s]",
						flightInfoId,msgTempleId, params.toString(), jumpParams.toString(), userId, redisKey);
	}
	
	
	public MessageRequest copy() {
		MessageRequest newBean = new MessageRequest();
		newBean.setAppid(this.getAppid());
		newBean.setChangeContent(this.getChangeContent());
		newBean.setChangeRedisKey(this.getChangeRedisKey());
		newBean.setFlightInfoId(this.getFlightInfoId());
		newBean.setJumpParams(this.getJumpParams());
		newBean.setMsgTempleId(this.getMsgTempleId());
		newBean.setParams(this.getParams());
		newBean.setPlanid(this.getPlanid());
		newBean.setRedisKey(this.getRedisKey());
		newBean.setReturnPoint(this.getReturnPoint());
		newBean.setToken(this.getToken());
		newBean.setReturnPrice(this.getReturnPrice());
		newBean.setUserId(this.getUserId());
		return newBean;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
