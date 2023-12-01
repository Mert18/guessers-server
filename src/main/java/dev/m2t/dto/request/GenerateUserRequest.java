package dev.m2t.dto.request;

public class GenerateUserRequest {
    private String wantedName;
    private Double wantedDollars;

    public String getWantedName() {
        return wantedName;
    }

    public void setWantedName(String wantedName) {
        this.wantedName = wantedName;
    }

    public Double getWantedDollars() {
        return wantedDollars;
    }

    public void setWantedDollars(Double wantedDollars) {
        this.wantedDollars = wantedDollars;
    }
}
