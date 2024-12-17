package dev.m2t.guessers.dto.response;

import dev.m2t.guessers.dto.client.nosyapi.LeagueEvent;

import java.util.List;

public class NosyAPIMatchesResponse {
    private String status;
    private String message;
    private String creditUsed;
    private List<LeagueEvent> data;

    public NosyAPIMatchesResponse() {

    }

    public NosyAPIMatchesResponse(String status, String message, String creditUsed, List<LeagueEvent> data) {
        this.status = status;
        this.message = message;
        this.creditUsed = creditUsed;
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

    public String getCreditUsed() {
        return creditUsed;
    }

    public void setCreditUsed(String creditUsed) {
        this.creditUsed = creditUsed;
    }

    public List<LeagueEvent> getData() {
        return data;
    }

    public void setData(List<LeagueEvent> data) {
        this.data = data;
    }
}
