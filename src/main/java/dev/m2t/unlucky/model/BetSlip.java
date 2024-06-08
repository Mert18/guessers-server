package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.SlipStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "betSlips")
public class BetSlip {
    @Id
    private String id;
    private List<Bet> bets;
    private String username;
    private LocalDateTime date;
    private Double totalOdds;
    private Double stakes;
    private Boolean won;
    private SlipStatusEnum status;
    private Boolean isPublic;

    public BetSlip() {

    }

    public BetSlip(List<Bet> bets, String username, LocalDateTime date, Double totalOdds, Double stakes, Boolean won, SlipStatusEnum status, Boolean isPublic) {
        this.bets = bets;
        this.username = username;
        this.date = date;
        this.totalOdds = totalOdds;
        this.stakes = stakes;
        this.status = status;
        this.won = won;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotalOdds() {
        return totalOdds;
    }

    public void setTotalOdds(Double totalOdds) {
        this.totalOdds = totalOdds;
    }

    public Double getStakes() {
        return stakes;
    }

    public void setStakes(Double stakes) {
        this.stakes = stakes;
    }

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public SlipStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SlipStatusEnum status) {
        this.status = status;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
