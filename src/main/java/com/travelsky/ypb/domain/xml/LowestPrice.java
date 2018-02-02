package com.travelsky.ypb.domain.xml;

import com.travelsky.ypb.model.lowestPriceOfPlan.CodeShare;
import com.travelsky.ypb.model.lowestPriceOfPlan.Taxes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "LowestPrice")
public class LowestPrice implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "Taxes")
	private com.travelsky.ypb.model.lowestPriceOfPlan.Taxes Taxes;
	
	@XmlElement(name = "fltno")
	private String fltno;
	
	@XmlElement(name = "airline")
	private String airline;
	
	@XmlElement(name = "deptm")
	private String deptm;

	@XmlElement(name = "arrtm")
	private String arrtm;
	
	@XmlElement(name = "dep")
	private String dep;
	
	@XmlElement(name = "arr")
	private String arr;
	
	@XmlElement(name = "week")
	private String week;
	
	@XmlElement(name = "cabin")
	private String cabin;
	
	@XmlElement(name = "bkclass")
	private String bkclass;
	
	@XmlElement(name = "av")
	private String av;

	@XmlElement(name = "disAmt")
	private String disAmt;
	
	@XmlElement(name = "yFareAmount")
	private String yFareAmount;

	@XmlElement(name = "returnPoint")
	private String returnPoint;
	
	@XmlElement(name = "discountPrice")
	private String discountPrice;
	
	@XmlElement(name = "discountValue")
	private String discountValue;
	
	@XmlElement(name="CodeShare")
	private CodeShare codeShare;
	
	
	@Override
	public String toString() {
		return "LowestPrice [Taxes=" + Taxes + ", fltno=" + fltno + ", airline=" + airline + ", deptm=" + deptm
				+ ", arrtm=" + arrtm + ", dep=" + dep + ", arr=" + arr + ", week=" + week + ", cabin=" + cabin
				+ ", bkclass=" + bkclass + ", av=" + av + ", disAmt=" + disAmt + ", yFareAmount=" + yFareAmount
				+ ", returnPoint=" + returnPoint + ", discountPrice=" + discountPrice + ", discountValue="
				+ discountValue + ", codeShare=" + codeShare + "]";
	}



	public LowestPrice() {
		super();
	}



	public LowestPrice(com.travelsky.ypb.model.lowestPriceOfPlan.Taxes taxes,
			String fltno, String airline, String deptm, String arrtm,
			String dep, String arr, String week, String cabin, String bkclass,
			String av, String disAmt, String yFareAmount, String returnPoint,
			String discountPrice, String discountValue) {
		super();
		Taxes = taxes;
		this.fltno = fltno;
		this.airline = airline;
		this.deptm = deptm;
		this.arrtm = arrtm;
		this.dep = dep;
		this.arr = arr;
		this.week = week;
		this.cabin = cabin;
		this.bkclass = bkclass;
		this.av = av;
		this.disAmt = disAmt;
		this.yFareAmount = yFareAmount;
		this.returnPoint = returnPoint;
		this.discountPrice = discountPrice;
		this.discountValue = discountValue;
	}




	
	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getDeptm() {
		return deptm;
	}

	public void setDeptm(String deptm) {
		this.deptm = deptm;
	}

	public String getArrtm() {
		return arrtm;
	}

	public void setArrtm(String arrtm) {
		this.arrtm = arrtm;
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

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(String disAmt) {
		this.disAmt = disAmt;
	}

	public String getYFareAmount() {
		return yFareAmount;
	}

	public void setYFareAmount(String yFareAmount) {
		this.yFareAmount = yFareAmount;
	}

	public Taxes getTaxes() {
		return Taxes;
	}

	public void setTaxes(Taxes taxes) {
		Taxes = taxes;
	}

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

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getArr() {
		return arr;
	}

	public void setArr(String arr) {
		this.arr = arr;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getReturnPoint() {
		return returnPoint;
	}

	public void setReturnPoint(String returnPoint) {
		this.returnPoint = returnPoint;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}



	public CodeShare getCodeShare() {
		return codeShare;
	}



	public void setCodeShare(CodeShare codeShare) {
		this.codeShare = codeShare;
	}
	
	
	
}
