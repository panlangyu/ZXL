package com.travelsky.ypb.domain.message;

import com.travelsky.ypb.domain.xml.LowestPrice;
import com.travelsky.ypb.model.airplan.YpbFlightPlan;

import java.util.List;
import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 * 整个processor流程所用数据模型
 */
public class Instance {

    private String flightDate;
    private String flightNo;
    private String departureAirport;
    private String departureAirportCn;
    private String arrivalAirport;
    private String arrivalAirportCn;
    private String airlineName;
    private String airlineCn;
    private String ticketCount;
    private Map cabinTicket;
    private String takeoffBegin;
    private String takeoffEnd;
    private String planid;
    private String weekday;
    private YpbFlightPlan flightPlan;
    private List<LowestPrice> lowestPrice;
    private Map<String,String> seamLess;

    private String appid;
    private String token;
    //消息模板ID
    private long msgTempleId;
    //消息模板中对应的参数
    private Map<String, String> params;
    //跳转参数 可附带到手机端
    private Map<String, String> jumpParams;
    //推送的用户ID
    private String userId;
    private double returnPoint;
    private double returnPrice;


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

    public YpbFlightPlan getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(YpbFlightPlan flightPlan) {
        this.flightPlan = flightPlan;
    }

    public List<LowestPrice> getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(List<LowestPrice> lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public Map<String, String> getSeamLess() {
        return seamLess;
    }

    public void setSeamLess(Map<String, String> seamLess) {
        this.seamLess = seamLess;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "flightDate='" + flightDate + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", departureAirportCn='" + departureAirportCn + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", arrivalAirportCn='" + arrivalAirportCn + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", airlineCn='" + airlineCn + '\'' +
                ", ticketCount='" + ticketCount + '\'' +
                ", cabinTicket=" + cabinTicket +
                ", takeoffBegin='" + takeoffBegin + '\'' +
                ", takeoffEnd='" + takeoffEnd + '\'' +
                ", planid='" + planid + '\'' +
                ", weekday='" + weekday + '\'' +
                ", flightPlan=" + flightPlan +
                ", lowestPrice=" + lowestPrice +
                ", seamLess=" + seamLess +
                ", appid='" + appid + '\'' +
                ", token='" + token + '\'' +
                ", msgTempleId=" + msgTempleId +
                ", params=" + params +
                ", jumpParams=" + jumpParams +
                ", userId='" + userId + '\'' +
                ", returnPoint=" + returnPoint +
                ", returnPrice=" + returnPrice +
                '}';
    }
}
