package com.travelsky.ypb.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * Created by huc on 2017/11/29.
 */
@Configuration
@PropertySource("classpath:messages.properties")
@ConfigurationProperties(prefix = "com.travelsky.ypb")
@Order(5)
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
    private String firstClassEn;
    private String businessClassEn;
    private String economyClassEN;
    private String CNY;
    private String slash;
    private String period;
    private String space;
    private String key;
    private String eventType5508;
    private String eventType5502;
    private String eventType5507;
    private String eventType5501;
    private String eventType5505;
    private String eventType5506;
    private String lowestPriceUrl;
    private String grabTicketUrl;
    private String tokenTimeOut;
    private String airline;
    private String colon;
    private String pTypeAirline;
    private String pTypeFlight;
    private String msgTime;
    private String requestParams;
    private String airLineCabinPolicy;
    private String redisCabinKey;
    private String goodMessages;
    private String badMessages;
    private String changeMessages;
    private String buyMessages;
    private String rosefor;
    private String left;
    private String decline;
    private String grabvotes;

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String mqExecutor;
    private int keepAlive;
    private String title5501;
    private String title5502;
    private String title5505;
    private String title5506;
    private String title5507;
    private String title5508;
    private String fullPrice;

    public String getEventType5505() {
        return eventType5505;
    }

    public void setEventType5505(String eventType5505) {
        this.eventType5505 = eventType5505;
    }

    public String getEventType5506() {
        return eventType5506;
    }

    public void setEventType5506(String eventType5506) {
        this.eventType5506 = eventType5506;
    }

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

    public String getpTypeAirline() {
        return pTypeAirline;
    }

    public void setpTypeAirline(String pTypeAirline) {
        this.pTypeAirline = pTypeAirline;
    }

    public String getpTypeFlight() {
        return pTypeFlight;
    }

    public void setpTypeFlight(String pTypeFlight) {
        this.pTypeFlight = pTypeFlight;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getFirstClassEn() {
        return firstClassEn;
    }

    public void setFirstClassEn(String firstClassEn) {
        this.firstClassEn = firstClassEn;
    }

    public String getBusinessClassEn() {
        return businessClassEn;
    }

    public void setBusinessClassEn(String businessClassEn) {
        this.businessClassEn = businessClassEn;
    }

    public String getEconomyClassEN() {
        return economyClassEN;
    }

    public void setEconomyClassEN(String economyClassEN) {
        this.economyClassEN = economyClassEN;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getAirLineCabinPolicy() {
        return airLineCabinPolicy;
    }

    public void setAirLineCabinPolicy(String airLineCabinPolicy) {
        this.airLineCabinPolicy = airLineCabinPolicy;
    }

    public String getRedisCabinKey() {
        return redisCabinKey;
    }

    public void setRedisCabinKey(String redisCabinKey) {
        this.redisCabinKey = redisCabinKey;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getMqExecutor() {
        return mqExecutor;
    }

    public void setMqExecutor(String mqExecutor) {
        this.mqExecutor = mqExecutor;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String getGoodMessages() {
        return goodMessages;
    }

    public void setGoodMessages(String goodMessages) {
        this.goodMessages = goodMessages;
    }

    public String getBadMessages() {
        return badMessages;
    }

    public void setBadMessages(String badMessages) {
        this.badMessages = badMessages;
    }

    public String getChangeMessages() {
        return changeMessages;
    }

    public void setChangeMessages(String changeMessages) {
        this.changeMessages = changeMessages;
    }

    public String getBuyMessages() {
        return buyMessages;
    }

    public void setBuyMessages(String buyMessages) {
        this.buyMessages = buyMessages;
    }

    public String getRosefor() {
        return rosefor;
    }

    public void setRosefor(String rosefor) {
        this.rosefor = rosefor;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getDecline() {
        return decline;
    }

    public void setDecline(String decline) {
        this.decline = decline;
    }

    public String getGrabvotes() {
        return grabvotes;
    }

    public void setGrabvotes(String grabvotes) {
        this.grabvotes = grabvotes;
    }

    public String getTitle5501() {
        return title5501;
    }

    public void setTitle5501(String title5501) {
        this.title5501 = title5501;
    }

    public String getTitle5502() {
        return title5502;
    }

    public void setTitle5502(String title5502) {
        this.title5502 = title5502;
    }

    public String getTitle5505() {
        return title5505;
    }

    public void setTitle5505(String title5505) {
        this.title5505 = title5505;
    }

    public String getTitle5506() {
        return title5506;
    }

    public void setTitle5506(String title5506) {
        this.title5506 = title5506;
    }

    public String getTitle5507() {
        return title5507;
    }

    public void setTitle5507(String title5507) {
        this.title5507 = title5507;
    }

    public String getTitle5508() {
        return title5508;
    }

    public void setTitle5508(String title5508) {
        this.title5508 = title5508;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }
}
