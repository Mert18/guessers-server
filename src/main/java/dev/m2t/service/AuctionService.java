package dev.m2t.service;

import dev.m2t.model.Auction;
import dev.m2t.model.Bid;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.websocket.Session;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuctionService {

    public Auction getActiveAuction() {
        List<Auction> auctions = Auction.list("active = true");
        if(auctions.isEmpty()) {
            throw new RuntimeException("No active auctions");
        }else if(auctions.size() > 1) {
            throw new RuntimeException("More than one active auction");
        }
        return auctions.get(0);
    }

    @Transactional
    public Auction handleReceivedBid(Bid bid) {
        Auction auction = Auction.findById(bid.getAuctionId());
        if(auction == null) {
            Log.error("Auction not found");
            throw new RuntimeException("Auction not found");
        }else if(bid.getBid() <= auction.getCurrentBid()) {
            Log.error("Bid must be higher than the current highest bid");
            throw new RuntimeException("Bid must be higher than the current highest bid");
        }
        auction.setCurrentBid(bid.getBid());
        auction.setCurrentBidder(bid.getBidder());
        auction.setAuctionEnd(auction.getAuctionEnd() + 2500 * 4);
        auction.persist();
        Log.info("Incoming bid updated the auction: " + auction);
        return auction;
    }

    @Transactional
    public Auction handleAuctionEnd(Auction auction) {
        auction.setActive(false);
        auction.persist();

        Log.info("Auction ended: " + auction);
        return auction;
    }
}
