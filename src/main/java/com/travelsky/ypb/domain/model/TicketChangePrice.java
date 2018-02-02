package com.travelsky.ypb.domain.model;

import java.io.Serializable;

/**
 * 价格变动
 * Created by huc on 2017/12/5.
 */
@SuppressWarnings("ALL")
public class TicketChangePrice implements Serializable{

    private String id;
    private String planId;
    private String dept;
    private String dest;
    private String flightDate;
    private String userId; // 预留字段
    private String pType;
    private String clazz;
    private String takeOffBegin;
    private String takeOffEnd;
    private String appId;
    private String isShare;
    private String lastPrice;
    private String thisPrice;
    private String shareFlightNo;
    private String flightNo;
    private String lastId;
    private String firstClass;
    private String businessClass;
    private String economyClass;
    private String airlineCode;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlandId() {
        return planId;
    }

    public void setPlandId(String plandId) {
        this.planId = plandId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getTakeOffBegin() {
        return takeOffBegin;
    }

    public void setTakeOffBegin(String takeOffBegin) {
        this.takeOffBegin = takeOffBegin;
    }

    public String getTakeOffEnd() {
        return takeOffEnd;
    }

    public void setTakeOffEnd(String takeOffEnd) {
        this.takeOffEnd = takeOffEnd;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(String thisPrice) {
        this.thisPrice = thisPrice;
    }

    public String getShareFlightNo() {
        return shareFlightNo;
    }

    public void setShareFlightNo(String shareFlightNo) {
        this.shareFlightNo = shareFlightNo;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

    public String getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(String economyClass) {
        this.economyClass = economyClass;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TicketChangePrice{" +
                "id='" + id + '\'' +
                ", plandId='" + planId + '\'' +
                ", dept='" + dept + '\'' +
                ", dest='" + dest + '\'' +
                ", flightDate='" + flightDate + '\'' +
                ", userId='" + userId + '\'' +
                ", pType='" + pType + '\'' +
                ", clazz='" + clazz + '\'' +
                ", takeOffBegin='" + takeOffBegin + '\'' +
                ", takeOffEnd='" + takeOffEnd + '\'' +
                ", appId='" + appId + '\'' +
                ", isShare='" + isShare + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", thisPrice='" + thisPrice + '\'' +
                ", shareFlightNo='" + shareFlightNo + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", lastId='" + lastId + '\'' +
                ", firstClass='" + firstClass + '\'' +
                ", businessClass='" + businessClass + '\'' +
                ", economyClass='" + economyClass + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }


}
