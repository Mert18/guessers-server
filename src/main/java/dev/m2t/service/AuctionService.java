package dev.m2t.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dev.m2t.model.Auction;
import dev.m2t.model.Bid;
import dev.m2t.model.Item;
import dev.m2t.model.User;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuctionService {

//    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(cron = "0 */5 * ? * *")
    public void auctionHandler () {
        Auction activeAuction = getActiveAuction();
        if(activeAuction.getAuctionEnd().isBefore(LocalDateTime.now())) {
            handleAuctionEnd(activeAuction);
            handleNewAuction();
        }
    }

    private Item generateNewItem() {
        Item item = new Item();
        item.setName("Item " + generateUniqueId());
        item.setPhotoUrl("https://picsum.photos/200/300");
        item.setStartingPrice(new Random().nextDouble() * 100);
        item.setSold(false);
        item.setItemId(generateUniqueId());
        item.persist();
        return item;
    }
    public Auction handleNewAuction() {
        Auction activeAuction = getActiveAuction();

        if(activeAuction != null) {
            Log.error("Active auction already exists");
            return null;
        }
        Auction newAuction = new Auction();
        newAuction.setActive(true);
        Item item = Item.find("sold = ?1", false).firstResult();
        if(item == null) {
            Log.error("No items available for auction");
            item = generateNewItem();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextDayStart = now.plusMinutes(5);
        newAuction.setAuctionEnd(nextDayStart);
        newAuction.setItemId(item.getItemId());
        newAuction.setItemName(item.getName());
        newAuction.setItemPhotoUrl(item.getPhotoUrl());
        newAuction.setAuctionId(generateUniqueId());
        newAuction.setCurrentBid(item.getStartingPrice());
        newAuction.setSold(false);
        newAuction.setCurrentBidder("N/A");
        newAuction.persist();

        Log.info("New auction created: " + newAuction);
        return newAuction;
    }

    public Auction getActiveAuction() {
        List<Auction> auctions = Auction.list("active", true);
        if(auctions.isEmpty()) {
            return null;
        }else if(auctions.size() > 1) {
            throw new RuntimeException("More than one active auction");
        }
        return auctions.get(0);
    }

    public void handleReceivedBid(Bid bid) {
        Auction auction = Auction.find("auctionId = ?1", bid.getAuctionId()).firstResult();

        if(auction == null) {
            Log.error("Auction not found");
            throw new RuntimeException("Auction not found");
        }else if(bid.getBid() <= auction.getCurrentBid()) {
            Log.error("Bid must be higher than the current highest bid");
            throw new RuntimeException("Bid must be higher than the current highest bid");
        }
        auction.setCurrentBid(bid.getBid());
        auction.setCurrentBidder(bid.getBidder());
        auction.update();

        Log.info("Incoming bid updated the auction: " + auction);
    }

    public void handleAuctionEnd(Auction auction) {
        Item item = Item.find("itemId = ?1", auction.getItemId()).firstResult();
        item.setSold(true);
        item.update();
        User user = User.find("username = ?1", auction.getCurrentBidder()).firstResult();
        user.setBalance(user.getBalance() - auction.getCurrentBid());
        user.update();
        auction.setSoldTo(user.getName());
        auction.setCompleted(true);
        auction.setSold(true);
        auction.setActive(false);
        auction.persist();
        Log.info("Auction ended: " + auction);
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}