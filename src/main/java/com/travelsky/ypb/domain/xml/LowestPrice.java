package com.travelsky.ypb.domain.xml;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 解析最低价XML
 * @see LowestPrice
 */
@XmlRootElement
@XmlType(propOrder = {"fltno","airline","deptm","week","cabin","bkclass","av","disAmt","yFareAmount","returnPoint","discountPrice","discountValue"})
public class LowestPrice {
	
	private String fltno;
	private String airline;
	private String deptm;
	private String week;
	private String cabin;
	private String bkclass;
	//余票量
	private String av;
	private Float disAmt;
	private Float yFareAmount;
	private Float returnPoint;
	private Float discountPrice;
	private Float discountValue;
	public String getFltno() {
		return fltno;
	}
	public void setFltno(String fltno) {
		this.fltno = fltno;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getDeptm() {
		return deptm;
	}
	public void setDeptm(String deptm) {
		this.deptm = deptm;
	}
	@Override
	public String toString() {
		return String
				.format("LowestPrice [fltno=%s, airline=%s, deptm=%s, week=%s, cabin=%s, bkclass=%s, av=%s, disAmt=%s, yFareAmount=%s, returnPoint=%s, discountPrice=%s, discountValue=%s]",
						fltno, airline, deptm, week, cabin, bkclass, av,
						disAmt, yFareAmount, returnPoint, discountPrice,
						discountValue);
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getCabin() {
		return cabin;
	}
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	public String getBkclass() {
		return bkclass;
	}
	public void setBkclass(String bkclass) {
		this.bkclass = bkclass;
	}
	public String getAv() {
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public Float getDisAmt() {
		return disAmt;
	}
	public void setDisAmt(Float disAmt) {
		this.disAmt = disAmt;
	}
	public Float getyFareAmount() {
		return yFareAmount;
	}
	public void setyFareAmount(Float yFareAmount) {
		this.yFareAmount = yFareAmount;
	}
	public Float getReturnPoint() {
		return returnPoint;
	}
	public void setReturnPoint(Float returnPoint) {
		this.returnPoint = returnPoint;
	}
	public Float getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Float discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Float getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(Float discountValue) {
		this.discountValue = discountValue;
	}
	
}
