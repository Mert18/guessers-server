package dev.m2t.guessers.dto.client.nosyapi;

import java.util.List;

public class MatchesResponse {
    private String status;
    private String message;
    private List<SingleMatch> data;

    public MatchesResponse() {

    }

    public MatchesResponse(String status, String message, List<SingleMatch> data) {
        this.status = status;
        this.message = message;
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

    public List<SingleMatch> getData() {
        return data;
    }

    public void setData(List<SingleMatch> data) {
        this.data = data;
    }
}
