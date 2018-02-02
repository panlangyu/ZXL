package com.travelsky.ypb.domain.model;

import java.io.Serializable;

/**
 * create CityAirlineCode by huc
 * 2017/12/7  上午9:07
 */
@SuppressWarnings("ALL")
public class CityAirlineCode implements Serializable {

    private String firstLetterPY;
    private String firstLetterEN;
    private String airportNameEn;
    private String cityName;
    private String countryName;
    private String airportName;
    private String cityNameEN;
    private String latitude;
    private String cityNamePY;
    private String cityCode;
    private String airportCode;
    private String countryId;
    private String longitude;
    private String hotFlag;
    private String flag;
    private String cityAirportName;
    private String cityNameJP;
    private String cityId;

    public CityAirlineCode() {
        super();
    }
    public CityAirlineCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getFirstLetterPY() {
        return firstLetterPY;
    }

    public void setFirstLetterPY(String firstLetterPY) {
        this.firstLetterPY = firstLetterPY;
    }

    public String getFirstLetterEN() {
        return firstLetterEN;
    }

    public void setFirstLetterEN(String firstLetterEN) {
        this.firstLetterEN = firstLetterEN;
    }

    public String getAirportNameEn() {
        return airportNameEn;
    }

    public void setAirportNameEn(String airportNameEn) {
        this.airportNameEn = airportNameEn;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCityNameEN() {
        return cityNameEN;
    }

    public void setCityNameEN(String cityNameEN) {
        this.cityNameEN = cityNameEN;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityNamePY() {
        return cityNamePY;
    }

    public void setCityNamePY(String cityNamePY) {
        this.cityNamePY = cityNamePY;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(String hotFlag) {
        this.hotFlag = hotFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCityAirportName() {
        return cityAirportName;
    }

    public void setCityAirportName(String cityAirportName) {
        this.cityAirportName = cityAirportName;
    }

    public String getCityNameJP() {
        return cityNameJP;
    }

    public void setCityNameJP(String cityNameJP) {
        this.cityNameJP = cityNameJP;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "CityAirlineCode{" +
                "firstLetterPY='" + firstLetterPY + '\'' +
                ", firstLetterEN='" + firstLetterEN + '\'' +
                ", airportNameEn='" + airportNameEn + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", airportName='" + airportName + '\'' +
                ", cityNameEN='" + cityNameEN + '\'' +
                ", latitude='" + latitude + '\'' +
                ", cityNamePY='" + cityNamePY + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", countryId='" + countryId + '\'' +
                ", longitude='" + longitude + '\'' +
                ", hotFlag='" + hotFlag + '\'' +
                ", flag='" + flag + '\'' +
                ", cityAirportName='" + cityAirportName + '\'' +
                ", cityNameJP='" + cityNameJP + '\'' +
                ", cityId='" + cityId + '\'' +
                '}';
    }
}
