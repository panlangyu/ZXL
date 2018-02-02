package com.travelsky.ypb.domain.model;

import java.io.Serializable;

/**
 * create FlightPlan by huc
 * 2017/12/7  下午4:20
 */
public class FlightPlan implements Serializable{

    private static final long serialVersionUID = 1L;
    private String planid;
    private String userid;
    private String appid;
    private String mobile;
    private String ptype;
    private String deptcity;
    private String destcity;
    private String flightno;
    private String flightdate;
    private String pekflydate;
    private String airlinecodes;
    private String equipmentcode;
    private String equipmentage;
    private String discount;
    private String clazz;
    private String takeoffbucketing;
    private String takeoffbegin;
    private String takeoffend;
    private String codeShare;
    private String fcabinticketcount;
    private String ccabinticketcount;
    private String ycabinticketcount;
    private String totalcabinticketcount;
    private String minprice;
    private String minpricediscount;
    private String mindiscountticketcount;
    private String mindiscountclazz;
    private String mindiscountcabin;
    private String termdep;
    private String termarr;
    private String createdby;
    private String createdtime;
    private String updatedby;
    private String updatedtime;
    private String deleteflag;

    public FlightPlan() {
    }

    public FlightPlan(String planid) {
        this.planid = planid;
    }

    public FlightPlan(String deptcity, String destcity, String flightdate) {
        this.deptcity = deptcity;
        this.destcity = destcity;
        this.flightdate = flightdate;
    }

    public String getPlanid() {
        return this.planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPtype() {
        return this.ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getDeptcity() {
        return this.deptcity;
    }

    public void setDeptcity(String deptcity) {
        this.deptcity = deptcity;
    }

    public String getDestcity() {
        return this.destcity;
    }

    public void setDestcity(String destcity) {
        this.destcity = destcity;
    }

    public String getFlightno() {
        return this.flightno;
    }

    public void setFlightno(String flightno) {
        this.flightno = flightno;
    }

    public String getFlightdate() {
        return this.flightdate;
    }

    public void setFlightdate(String flightdate) {
        this.flightdate = flightdate;
    }

    public String getPekflydate() {
        return this.pekflydate;
    }

    public void setPekflydate(String pekflydate) {
        this.pekflydate = pekflydate;
    }

    public String getAirlinecodes() {
        return this.airlinecodes;
    }

    public void setAirlinecodes(String airlinecodes) {
        this.airlinecodes = airlinecodes;
    }

    public String getEquipmentcode() {
        return this.equipmentcode;
    }

    public void setEquipmentcode(String equipmentcode) {
        this.equipmentcode = equipmentcode;
    }

    public String getEquipmentage() {
        return this.equipmentage;
    }

    public void setEquipmentage(String equipmentage) {
        this.equipmentage = equipmentage;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getClazz() {
        return this.clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getTakeoffbucketing() {
        return this.takeoffbucketing;
    }

    public void setTakeoffbucketing(String takeoffbucketing) {
        this.takeoffbucketing = takeoffbucketing;
    }

    public String getTakeoffbegin() {
        return this.takeoffbegin;
    }

    public void setTakeoffbegin(String takeoffbegin) {
        this.takeoffbegin = takeoffbegin;
    }

    public String getTakeoffend() {
        return this.takeoffend;
    }

    public void setTakeoffend(String takeoffend) {
        this.takeoffend = takeoffend;
    }

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCodeShare() {
        return this.codeShare;
    }

    public void setCodeShare(String codeShare) {
        this.codeShare = codeShare;
    }

    public String getFcabinticketcount() {
        return this.fcabinticketcount;
    }

    public void setFcabinticketcount(String fcabinticketcount) {
        this.fcabinticketcount = fcabinticketcount;
    }

    public String getCcabinticketcount() {
        return this.ccabinticketcount;
    }

    public void setCcabinticketcount(String ccabinticketcount) {
        this.ccabinticketcount = ccabinticketcount;
    }

    public String getYcabinticketcount() {
        return this.ycabinticketcount;
    }

    public void setYcabinticketcount(String ycabinticketcount) {
        this.ycabinticketcount = ycabinticketcount;
    }

    public String getTotalcabinticketcount() {
        return this.totalcabinticketcount;
    }

    public void setTotalcabinticketcount(String totalcabinticketcount) {
        this.totalcabinticketcount = totalcabinticketcount;
    }

    public String getMinprice() {
        return this.minprice;
    }

    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

    public String getMinpricediscount() {
        return this.minpricediscount;
    }

    public void setMinpricediscount(String minpricediscount) {
        this.minpricediscount = minpricediscount;
    }

    public String getMindiscountticketcount() {
        return this.mindiscountticketcount;
    }

    public void setMindiscountticketcount(String mindiscountticketcount) {
        this.mindiscountticketcount = mindiscountticketcount;
    }

    public String getMindiscountclazz() {
        return this.mindiscountclazz;
    }

    public void setMindiscountclazz(String mindiscountclazz) {
        this.mindiscountclazz = mindiscountclazz;
    }

    public String getMindiscountcabin() {
        return this.mindiscountcabin;
    }

    public void setMindiscountcabin(String mindiscountcabin) {
        this.mindiscountcabin = mindiscountcabin;
    }

    public String getTermdep() {
        return this.termdep;
    }

    public void setTermdep(String termdep) {
        this.termdep = termdep;
    }

    public String getTermarr() {
        return this.termarr;
    }

    public void setTermarr(String termarr) {
        this.termarr = termarr;
    }

    public String getCreatedby() {
        return this.createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getUpdatedby() {
        return this.updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getUpdatedtime() {
        return this.updatedtime;
    }

    public void setUpdatedtime(String updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getDeleteflag() {
        return this.deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    @Override
    public String toString() {
        return "FlightPlan{" +
                "planid='" + planid + '\'' +
                ", userid='" + userid + '\'' +
                ", appid='" + appid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ptype='" + ptype + '\'' +
                ", deptcity='" + deptcity + '\'' +
                ", destcity='" + destcity + '\'' +
                ", flightno='" + flightno + '\'' +
                ", flightdate='" + flightdate + '\'' +
                ", pekflydate='" + pekflydate + '\'' +
                ", airlinecodes='" + airlinecodes + '\'' +
                ", equipmentcode='" + equipmentcode + '\'' +
                ", equipmentage='" + equipmentage + '\'' +
                ", discount='" + discount + '\'' +
                ", clazz='" + clazz + '\'' +
                ", takeoffbucketing='" + takeoffbucketing + '\'' +
                ", takeoffbegin='" + takeoffbegin + '\'' +
                ", takeoffend='" + takeoffend + '\'' +
                ", codeShare='" + codeShare + '\'' +
                ", fcabinticketcount='" + fcabinticketcount + '\'' +
                ", ccabinticketcount='" + ccabinticketcount + '\'' +
                ", ycabinticketcount='" + ycabinticketcount + '\'' +
                ", totalcabinticketcount='" + totalcabinticketcount + '\'' +
                ", minprice='" + minprice + '\'' +
                ", minpricediscount='" + minpricediscount + '\'' +
                ", mindiscountticketcount='" + mindiscountticketcount + '\'' +
                ", mindiscountclazz='" + mindiscountclazz + '\'' +
                ", mindiscountcabin='" + mindiscountcabin + '\'' +
                ", termdep='" + termdep + '\'' +
                ", termarr='" + termarr + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", updatedby='" + updatedby + '\'' +
                ", updatedtime='" + updatedtime + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                '}';
    }
}
