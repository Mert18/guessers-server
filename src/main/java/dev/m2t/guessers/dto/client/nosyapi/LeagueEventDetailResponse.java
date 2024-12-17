package dev.m2t.guessers.dto.client.nosyapi;

import java.util.List;

public class LeagueEventDetailResponse {
    private String status;
    private String message;
    private Long systemTime;
    private List<LeagueEvent> data;

    public LeagueEventDetailResponse() {

    }

    public LeagueEventDetailResponse(String status, String message, Long systemTime, List<LeagueEvent> data) {
        this.status = status;
        this.message = message;
        this.systemTime = systemTime;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Long systemTime) {
        this.systemTime = systemTime;
    }

    public List<LeagueEvent> getData() {
        return data;
    }

    public void setData(List<LeagueEvent> data) {
        this.data = data;
    }
}
