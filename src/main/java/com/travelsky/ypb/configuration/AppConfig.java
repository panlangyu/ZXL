package com.travelsky.ypb.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by huc on 2017/11/29.
 */
@Configuration
@PropertySource("classpath:messages.properties")
@ConfigurationProperties(prefix = "com.travelsky.ypb")
public class AppConfig {

    private String appId;
    private String pushUrl;
    private String getTokenUrl;
    private String ticketCountCn;
    private String parenthesesCodeLeft;
    private String parenthesesCodeRight;
    private String setOff;
    private String bar;
    private String comma;
    private String of;
    private String flight;
    private String has;
    private String page;
    private String lowestPrice;
    private String discount;
    private String firstClass;
    private String businessClass;
    private String economyClass;
    private String CNY;
    private String slash;
    private String period;
    private String space;
    private String key;
    private String eventType5508;
    private String eventType5502;
    private String eventType5507;
    private String eventType5501;
    private String lowestPriceUrl;
    private String grabTicketUrl;
    private String tokenTimeOut;
    private String airline;
    private String colon;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getTicketCountCn() {
        return ticketCountCn;
    }

    public void setTicketCountCn(String ticketCountCn) {
        this.ticketCountCn = ticketCountCn;
    }

    public String getParenthesesCodeLeft() {
        return parenthesesCodeLeft;
    }

    public void setParenthesesCodeLeft(String parenthesesCodeLeft) {
        this.parenthesesCodeLeft = parenthesesCodeLeft;
    }

    public String getParenthesesCodeRight() {
        return parenthesesCodeRight;
    }

    public void setParenthesesCodeRight(String parenthesesCodeRight) {
        this.parenthesesCodeRight = parenthesesCodeRight;
    }

    public String getSetOff() {
        return setOff;
    }

    public void setSetOff(String setOff) {
        this.setOff = setOff;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getComma() {
        return comma;
    }

    public void setComma(String comma) {
        this.comma = comma;
    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getCNY() {
        return CNY;
    }

    public void setCNY(String CNY) {
        this.CNY = CNY;
    }

    public String getSlash() {
        return slash;
    }

    public void setSlash(String slash) {
        this.slash = slash;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEventType5508() {
        return eventType5508;
    }

    public void setEventType5508(String eventType5508) {
        this.eventType5508 = eventType5508;
    }

    public String getEventType5502() {
        return eventType5502;
    }

    public void setEventType5502(String eventType5502) {
        this.eventType5502 = eventType5502;
    }

    public String getEventType5507() {
        return eventType5507;
    }

    public void setEventType5507(String eventType5507) {
        this.eventType5507 = eventType5507;
    }

    public String getEventType5501() {
        return eventType5501;
    }

    public void setEventType5501(String eventType5501) {
        this.eventType5501 = eventType5501;
    }

    public String getLowesPriceUrl() {
        return lowestPriceUrl;
    }

    public void setLowesPriceUrl(String lowesPriceUrl) {
        this.lowestPriceUrl = lowesPriceUrl;
    }

    public String getGrabTicketUrl() {
        return grabTicketUrl;
    }

    public void setGrabTicketUrl(String grabTicketUrl) {
        this.grabTicketUrl = grabTicketUrl;
    }

    public String getLowestPriceUrl() {
        return lowestPriceUrl;
    }

    public void setLowestPriceUrl(String lowestPriceUrl) {
        this.lowestPriceUrl = lowestPriceUrl;
    }

    public String getTokenTimeOut() {
        return tokenTimeOut;
    }

    public void setTokenTimeOut(String tokenTimeOut) {
        this.tokenTimeOut = tokenTimeOut;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getColon() {
        return colon;
    }

    public void setColon(String colon) {
        this.colon = colon;
    }
}
