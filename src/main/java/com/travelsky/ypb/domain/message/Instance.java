package com.travelsky.ypb.domain.message;

import com.travelsky.ypb.domain.model.FlightPlan;
import com.travelsky.ypb.domain.model.TicketChangePrice;
import com.travelsky.ypb.model.lowestPriceOfPlan.LowestPrice;
import org.apache.commons.httpclient.NameValuePair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 * 整个processor流程所用数据模型
 */
public class Instance {

    private String flightDate;             //起飞日期
    private String flightNo;               //航班号
    private String departureAirport;       //出发地
    private String departureAirportCn;     //出发地中文
    private String arrivalAirport;         //到达地
    private String arrivalAirportCn;       //到达地中文
    private String airlineName;            //航空公司
    private String airlineCn;              //航空公司中文
    private String ticketCountSum;         //总票量
    private Map cabinTicket;               //seamless
    private String takeoffBegin;           //起飞时间
    private String takeoffEnd;             //到达时间
    private String planid;                 //计划ID
    private String weekday;                //星期
    private FlightPlan flightPlan;         //飞行计划
    private List<LowestPrice> lowestPrice; //最低价
    private Map seamLess;                  //eamless
    private NameValuePair[] value;         //拼装参数
    private Map planMap;                   //飞行计划
    private String pType;                  //计划类型
    private LowestPrice price;             //最低价
    private String ticketCount;            //Y仓余票
    private String shareFlightNo;          //共享航班主飞航班号
    private List<FlightPlan> flightPlanList;//飞行计划
    private TicketChangePrice ticketChangePrice;//价格变动
    private String[] appid;                // appid
    private String token;                  // toke
    private long msgTempleId;              //模版ID
    private Map<String, String> params;    //模版参数
    private Map<String, String> jumpParams;//客户端跳转参数
    private String[]  userId;              //userId
    private double returnPoint;            //折扣
    private double returnPrice;            //折扣价
    private String eventType;              //时间类型
    private String messagesBody;           //信息体
    private String originalMessage;       //原始报文
    private String title;

    public TicketChangePrice getTicketChangePrice() {
        return ticketChangePrice;
    }

    public void setTicketChangePrice(TicketChangePrice ticketChangePrice) {
        this.ticketChangePrice = ticketChangePrice;
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

    public Map getCabinTicket() {
        return cabinTicket;
    }

    public void setCabinTicket(Map cabinTicket) {
        this.cabinTicket = cabinTicket;
    }

    public String getTakeoffBegin() {
        return takeoffBegin;
    }

    public void setTakeoffBegin(String takeoffBegin) {
        this.takeoffBegin = takeoffBegin;
    }

    public String getTakeoffEnd() {
        return takeoffEnd;
    }

    public void setTakeoffEnd(String takeoffEnd) {
        this.takeoffEnd = takeoffEnd;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getAirlineCn() {
        return airlineCn;
    }

    public void setAirlineCn(String airlineCn) {
        this.airlineCn = airlineCn;
    }

    public String getDepartureAirportCn() {
        return departureAirportCn;
    }

    public void setDepartureAirportCn(String departureAirportCn) {
        this.departureAirportCn = departureAirportCn;
    }

    public String getArrivalAirportCn() {
        return arrivalAirportCn;
    }

    public void setArrivalAirportCn(String arrivalAirportCn) {
        this.arrivalAirportCn = arrivalAirportCn;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(FlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    public List<LowestPrice> getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(List<LowestPrice> lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String[] getAppid() {
        return appid;
    }

    public void setAppid(String[] appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String[] getUserId() {
        return userId;
    }

    public void setUserId(String[] userId) {
        this.userId = userId;
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

    public Map getSeamLess() {
        return seamLess;
    }

    public void setSeamLess(Map seamLess) {
        this.seamLess = seamLess;
    }

    public NameValuePair[] getValue() {
        return value;
    }

    public void setValue(NameValuePair[] value) {
        this.value = value;
    }

    public Map getPlanMap() {
        return planMap;
    }

    public void setPlanMap(Map planMap) {
        this.planMap = planMap;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public List<FlightPlan> getFlightPlanList() {
        return flightPlanList;
    }

    public void setFlightPlanList(List<FlightPlan> flightPlanList) {
        this.flightPlanList = flightPlanList;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LowestPrice getPrice() {
        return price;
    }

    public void setPrice(LowestPrice price) {
        this.price = price;
    }

    public String getTicketCountSum() {
        return ticketCountSum;
    }

    public void setTicketCountSum(String ticketCountSum) {
        this.ticketCountSum = ticketCountSum;
    }

    public String getShareFlightNo() {
        return shareFlightNo;
    }

    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    public String getMessagesBody() {
        return messagesBody;
    }

    public void setMessagesBody(String messagesBody) {
        this.messagesBody = messagesBody;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Instance{");
        sb.append("flightDate='").append(flightDate).append('\'');
        sb.append(", flightNo='").append(flightNo).append('\'');
        sb.append(", departureAirport='").append(departureAirport).append('\'');
        sb.append(", departureAirportCn='").append(departureAirportCn).append('\'');
        sb.append(", arrivalAirport='").append(arrivalAirport).append('\'');
        sb.append(", arrivalAirportCn='").append(arrivalAirportCn).append('\'');
        sb.append(", airlineName='").append(airlineName).append('\'');
        sb.append(", airlineCn='").append(airlineCn).append('\'');
        sb.append(", ticketCountSum='").append(ticketCountSum).append('\'');
        sb.append(", cabinTicket=").append(cabinTicket);
        sb.append(", takeoffBegin='").append(takeoffBegin).append('\'');
        sb.append(", takeoffEnd='").append(takeoffEnd).append('\'');
        sb.append(", planid='").append(planid).append('\'');
        sb.append(", weekday='").append(weekday).append('\'');
        sb.append(", flightPlan=").append(flightPlan);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", seamLess=").append(seamLess);
        sb.append(", value=").append(value == null ? "null" : Arrays.asList(value).toString());
        sb.append(", planMap=").append(planMap);
        sb.append(", pType='").append(pType).append('\'');
        sb.append(", price=").append(price);
        sb.append(", ticketCount='").append(ticketCount).append('\'');
        sb.append(", shareFlightNo='").append(shareFlightNo).append('\'');
        sb.append(", flightPlanList=").append(flightPlanList);
        sb.append(", ticketChangePrice=").append(ticketChangePrice);
        sb.append(", appid=").append(appid == null ? "null" : Arrays.asList(appid).toString());
        sb.append(", token='").append(token).append('\'');
        sb.append(", msgTempleId=").append(msgTempleId);
        sb.append(", params=").append(params);
        sb.append(", jumpParams=").append(jumpParams);
        sb.append(", userId=").append(userId == null ? "null" : Arrays.asList(userId).toString());
        sb.append(", returnPoint=").append(returnPoint);
        sb.append(", returnPrice=").append(returnPrice);
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", messagesBody='").append(messagesBody).append('\'');
        sb.append(", originalMessage='").append(originalMessage).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
