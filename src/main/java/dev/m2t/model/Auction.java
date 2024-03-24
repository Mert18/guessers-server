package dev.m2t.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.time.Duration;
import java.time.LocalDateTime;

@MongoEntity(collection="auctions")
public class Auction extends PanacheMongoEntity {
    private String itemId;
    private String auctionId;
    private Double currentBid;
    private String currentBidder;
    private Boolean sold;
    private LocalDateTime auctionEnd;
    private Duration auctionDuration;
    private String soldTo;
    private Boolean active;
    private String itemPhotoUrl;
    private String itemName;
    private Boolean completed;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
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

    public LocalDateTime getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(LocalDateTime auctionEnd) {
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

    public String getItemPhotoUrl() {
        return itemPhotoUrl;
    }

    public void setItemPhotoUrl(String itemPhotoUrl) {
        this.itemPhotoUrl = itemPhotoUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
