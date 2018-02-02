package com.travelsky.ypb.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 标准舱位
 * @author ykpang
 *
 */
@SuppressWarnings("ALL")
public class Cabin  implements Serializable{
	//航空公司二字码
    private String airlinecode;
    //id
    private BigDecimal airCabinId;
    //中文名称
    private String airlinezhname;
    //中文简称
    private String showairlinezhname;
    //英文名称
    private String airlineenname;
    //超级头等舱
    private String supfirstcabin;
    //头等舱
    private String firstcabin;
    //超级商务舱
    private String supbusinesscabin;
    //商务舱
    private String businesscabin;
    //超级经济舱
    private String supeconomycabin;
    //经济舱
    private String economycabin;

    private String createdby;

    private Date createddate;

    private String updateby;

    private Date updatedate;

    public String getAirlinecode() {
        return airlinecode;
    }

    public void setAirlinecode(String airlinecode) {
        this.airlinecode = airlinecode == null ? null : airlinecode.trim();
    }

    public BigDecimal getAirCabinId() {
        return airCabinId;
    }

    public void setAirCabinId(BigDecimal airCabinId) {
        this.airCabinId = airCabinId;
    }

    public String getAirlinezhname() {
        return airlinezhname;
    }

    public void setAirlinezhname(String airlinezhname) {
        this.airlinezhname = airlinezhname == null ? null : airlinezhname.trim();
    }

    public String getShowairlinezhname() {
        return showairlinezhname;
    }

    public void setShowairlinezhname(String showairlinezhname) {
        this.showairlinezhname = showairlinezhname == null ? null : showairlinezhname.trim();
    }

    public String getAirlineenname() {
        return airlineenname;
    }

    public void setAirlineenname(String airlineenname) {
        this.airlineenname = airlineenname == null ? null : airlineenname.trim();
    }

    public String getSupfirstcabin() {
        return supfirstcabin;
    }

    public void setSupfirstcabin(String supfirstcabin) {
        this.supfirstcabin = supfirstcabin == null ? null : supfirstcabin.trim();
    }

    public String getFirstcabin() {
        return firstcabin;
    }

    public void setFirstcabin(String firstcabin) {
        this.firstcabin = firstcabin == null ? null : firstcabin.trim();
    }

    public String getSupbusinesscabin() {
        return supbusinesscabin;
    }

    public void setSupbusinesscabin(String supbusinesscabin) {
        this.supbusinesscabin = supbusinesscabin == null ? null : supbusinesscabin.trim();
    }

    public String getBusinesscabin() {
        return businesscabin;
    }

    public void setBusinesscabin(String businesscabin) {
        this.businesscabin = businesscabin == null ? null : businesscabin.trim();
    }

    public String getSupeconomycabin() {
        return supeconomycabin;
    }

    public void setSupeconomycabin(String supeconomycabin) {
        this.supeconomycabin = supeconomycabin == null ? null : supeconomycabin.trim();
    }

    public String getEconomycabin() {
        return economycabin;
    }

    public void setEconomycabin(String economycabin) {
        this.economycabin = economycabin == null ? null : economycabin.trim();
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "Cabin{" +
                "airlinecode='" + airlinecode + '\'' +
                ", airCabinId=" + airCabinId +
                ", airlinezhname='" + airlinezhname + '\'' +
                ", showairlinezhname='" + showairlinezhname + '\'' +
                ", airlineenname='" + airlineenname + '\'' +
                ", supfirstcabin='" + supfirstcabin + '\'' +
                ", firstcabin='" + firstcabin + '\'' +
                ", supbusinesscabin='" + supbusinesscabin + '\'' +
                ", businesscabin='" + businesscabin + '\'' +
                ", supeconomycabin='" + supeconomycabin + '\'' +
                ", economycabin='" + economycabin + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createddate=" + createddate +
                ", updateby='" + updateby + '\'' +
                ", updatedate=" + updatedate +
                '}';
    }
}