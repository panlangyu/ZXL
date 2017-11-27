package com.travelsky.ypb.domain.message;

import java.util.Map;

/**
 * Created by huc on 2017/11/22.
 */
public class Instance {

    private String flightDate;
    private String flightNo;
    private String departureAirport;
    private String arrivalAirport;
    private String airlineName;
    private String ticketCount;
    private Map<String,String> cabinTicket;
    private String takeoffBegin;
    private String takeoffEnd;
    private String planid;


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

    public Map<String, String> getCabinTicket() {
        return cabinTicket;
    }

    public void setCabinTicket(Map<String, String> cabinTicket) {
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

    @Override
    public String toString() {
        return "Instance{" +
                "flightDate='" + flightDate + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", ticketCount='" + ticketCount + '\'' +
                ", cabinTicket=" + cabinTicket +
                ", takeoffBegin='" + takeoffBegin + '\'' +
                ", takeoffEnd='" + takeoffEnd + '\'' +
                ", planid='" + planid + '\'' +
                '}';
    }
}
