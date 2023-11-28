package dev.m2t.service;

import dev.m2t.model.Auction;
import dev.m2t.model.Bid;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AuctionService {

    public Auction handleReceivedBid(Bid bid) {
        Auction auction = Auction.findById(bid.getAuctionId());
        if(auction == null) {
            throw new RuntimeException("Auction not found");
        }else if(bid.getBid() <= auction.getCurrentBid()) {
            throw new RuntimeException("Bid must be higher than the current highest bid");
        }
        auction.setCurrentBid(bid.getBid());
        auction.setCurrentBidder(bid.getBidder());
        auction.setAuctionEnd(auction.getAuctionEnd() + 2500 * 4);
        auction.persist();
        return auction;
    }
}
