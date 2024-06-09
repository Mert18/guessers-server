package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.Bet;

import java.util.List;

public class CreateBetSlipRequest {
    private List<Bet> bets;
    private Double stakes;
    private Double totalOdds;
    private String roomId;

    public CreateBetSlipRequest() {
    }

    public CreateBetSlipRequest(List<Bet> bets, Double stakes,  Double totalOdds, String roomId) {
        this.bets = bets;
        this.stakes = stakes;
        this.totalOdds = totalOdds;
        this.roomId = roomId;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public Double getStakes() {
        return stakes;
    }

    public void setStakes(Double stakes) {
        this.stakes = stakes;
    }

    public Double getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(Double totalOdds) {
        this.totalOdds = totalOdds;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
