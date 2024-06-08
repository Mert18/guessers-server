package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.Bet;

import java.util.List;

public class CreateBetSlipRequest {
    private List<Bet> bets;
    private Double stakes;
    private Boolean isPublic;
    private Double totalOdds;

    public CreateBetSlipRequest() {
    }

    public CreateBetSlipRequest(List<Bet> bets, Double stakes, Boolean isPublic, Double totalOdds) {
        this.bets = bets;
        this.stakes = stakes;
        this.isPublic = isPublic;
        this.totalOdds = totalOdds;
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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Double getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(Double totalOdds) {
        this.totalOdds = totalOdds;
    }
}
