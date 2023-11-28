package dev.m2t.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.websocket.Session;

import java.io.IOException;

public class Bid extends PanacheEntity {
    @Column(name = "auction_id", nullable = false)
    private Long auctionId;
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(name = "bidder", nullable = false)
    private String bidder;
    @Column(name = "bid", nullable = false, precision = 2)
    private Double bid;

    public static void validator(Bid bid, Session session) throws IOException {
        Bid highestBid = find("auctionId = ?1 and itemId = ?2 order by bid desc", bid.auctionId, bid.itemId).firstResult();
        if (highestBid != null && bid.bid <= highestBid.bid) {
            session.getBasicRemote().sendText("Invalid bid");
            throw new RuntimeException("Bid must be higher than the current highest bid");
        }
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }
}
