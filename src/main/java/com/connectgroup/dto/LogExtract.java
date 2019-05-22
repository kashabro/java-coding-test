package com.connectgroup.dto;

public class LogExtract {

    private final long timeStamp;

    private final String countryCode;

    private final long responseTime;

    public LogExtract(long timeStamp, String countryCode, long responseTime) {
        this.timeStamp = timeStamp;
        this.countryCode = countryCode;
        this.responseTime = responseTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    @Override
    public String toString() {
        return "LogExtract{" +
                "timeStamp=" + timeStamp +
                ", countryCode='" + countryCode + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }
}
