package dev.m2t.dto.request;

public class GenerateUserRequest {
    private String wantedUsername;
    private Double wantedDollars;

    public String getWantedUsername() {
        return wantedUsername;
    }

    public void setWantedUsername(String wantedUsername) {
        this.wantedUsername = wantedUsername;
    }

    public Double getWantedDollars() {
        return wantedDollars;
    }

    public void setWantedDollars(Double wantedDollars) {
        this.wantedDollars = wantedDollars;
    }
}
