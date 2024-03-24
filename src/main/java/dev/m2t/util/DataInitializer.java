package dev.m2t.util;

import dev.m2t.model.Auction;
import dev.m2t.model.Item;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class DataInitializer {

    void onStart(@Observes StartupEvent ev) throws InterruptedException {
        insertAdditionalData();
    }

    // TODO: Remove this method in production
    void insertAdditionalData() throws InterruptedException {
        Auction.deleteAll();

        Item item = new Item();
        item.setItemId(1L);
        item.setName("Item 1");
        item.setStartingPrice(100.0);
        item.setPhotoUrl("https://picsum.photos/300/300");
        item.setSold(false);
        Item.persist(item);

        Auction auction = new Auction();
        auction.setActive(true);
        auction.setItemId(item.getItemId());
        auction.setAuctionId(1L);
        auction.setItemPhotoUrl(item.getPhotoUrl());
        auction.setCurrentBid(item.getStartingPrice());
        auction.setItemName(item.getName());
        auction.setCurrentBidder("N/A");
        auction.setAuctionEnd(System.currentTimeMillis() + 60000 * 180);
        auction.setSold(false);
        Auction.persist(auction);

        System.out.println("-------------------------------------------------");
        System.out.println("Data inserted...");
        System.out.println("-------------------------------------------------");
    }
}