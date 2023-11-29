package dev.m2t.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Duration;

@Entity
@Table(name = "auctions")
public class Auction extends PanacheEntity {
    private Long itemId;
    private Double currentBid;
    private String currentBidder;
    private Boolean sold;
    private Long auctionEnd;
    private Duration auctionDuration;
    private String soldTo;
    private Boolean active;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public String getCurrentBidder() {
        return currentBidder;
    }

    public void setCurrentBidder(String currentBidder) {
        this.currentBidder = currentBidder;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Long getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Long auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public Duration getAuctionDuration() {
        return auctionDuration;
    }

    public void setAuctionDuration(Duration auctionDuration) {
        this.auctionDuration = auctionDuration;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "itemId=" + itemId +
                ", currentBid=" + currentBid +
                ", currentBidder='" + currentBidder + '\'' +
                ", auctionEnd=" + auctionEnd +
                ", active=" + active +
                '}';
    }
}
