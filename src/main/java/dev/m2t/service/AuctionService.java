package dev.m2t.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dev.m2t.model.Auction;
import dev.m2t.model.Bid;
import dev.m2t.model.Item;
import dev.m2t.model.User;
import dev.m2t.websocket.AuctionWebSocket;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuctionService {

    @Inject
    AuctionWebSocket auctionWebSocket;

    private Jsonb jsonb = JsonbBuilder.create();

    @Scheduled(cron = "0 */5 * ? * *")
    public void auctionHandler () throws IOException {
        Log.info("Auction handler run");
        Auction activeAuction = getActiveAuction();
        if(activeAuction == null) {
            Log.info("No active auction, creating one...");
            handleNewAuction();
            return;
        }
            Log.info("Auction should end, ending the auction: " + activeAuction);
            handleAuctionEnd(activeAuction);
            Log.info("Creating a new auction...");
            handleNewAuction();
    }

    private Item generateNewItem() {
        Item item = new Item();
        item.setName("Item " + generateUniqueId());
        item.setPhotoUrl("https://picsum.photos/300/300");
        item.setStartingPrice(new Random().nextDouble() * 100);
        item.setSold(false);
        item.setItemId(generateUniqueId());
        item.persist();
        return item;
    }
    public Auction handleNewAuction() throws IOException {
        Log.info("Creating a new auction...");
        Auction activeAuction = getActiveAuction();

        if(activeAuction != null) {
            Log.error("An auction already exists. Cannot create a new one.");
            return null;
        }
        Auction newAuction = new Auction();
        newAuction.setActive(true);
        Item item = Item.find("sold = ?1", false).firstResult();
        if(item == null) {
            Log.error("No items available for auction. Generating new item...");
            item = generateNewItem();
        }
        newAuction.setAuctionEnd(getNextDivisibleByFiveMinutes(LocalDateTime.now()));
        newAuction.setItemId(item.getItemId());
        newAuction.setItemName(item.getName());
        newAuction.setItemPhotoUrl(item.getPhotoUrl());
        newAuction.setAuctionId(generateUniqueId());
        newAuction.setCurrentBid(item.getStartingPrice());
        newAuction.setSold(false);
        newAuction.setCurrentBidder(null);
        newAuction.persist();

        auctionWebSocket.broadcastToAll("{\"type\": \"auctionUpdate\", \"data\": " + jsonb.toJson(newAuction) + "}");

        Log.info("New auction created: " + newAuction);
        return newAuction;
    }

    public void clearAuctions() throws IOException {
        List<Auction> auctions = Auction.listAll();
        for(Auction auction : auctions) {
            auction.setActive(false);
            auction.setCompleted(false);
            auction.setSold(false);
            auction.update();
        }
        handleNewAuction();
    }

    public static LocalDateTime getNextDivisibleByFiveMinutes(LocalDateTime current) {
        if (current == null) {
            current = LocalDateTime.now();
        }

        int currentMinute = current.getMinute();
        int remainingMinute = 5 - (currentMinute % 5);
        return current.plusMinutes(remainingMinute).withSecond(0);
    }

    public Auction getActiveAuction() {
        List<Auction> auctions = Auction.list("active", true);
        if(auctions.isEmpty()) {
            return null;
        }else if(auctions.size() > 1) {
            Log.error("More than one active auction");

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
        Item item = Item.find("itemId = ?1", auction.getItemId()).singleResult();
        if(auction.getCurrentBidder() == null) {
            Log.info("No bids received for the auction. Item is not sold.");
            auction.setSold(false);
            auction.setActive(false);
            item.setSold(false);
            auction.update();
            return;
        }else {
            User user = User.find("name = ?1", auction.getCurrentBidder()).firstResult();
            user.setBalance(user.getBalance() - auction.getCurrentBid());
            user.update();
            auction.setSoldTo(user.getName());
            auction.setSold(true);
            auction.setActive(false);
            item.setSold(true);
            Log.info("Auction ended. Item is sold to " + user.getName() + " for " + auction.getCurrentBid());

        }
        auction.setCompleted(true);
        auction.setActive(false);

        item.update();
        auction.update();
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}