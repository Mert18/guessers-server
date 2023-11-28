package dev.m2t.util;

import dev.m2t.model.Auction;
import dev.m2t.model.Item;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class FirstAuctionStarter {
    @Inject
    EntityManager entityManager;

    void onStart(@Observes StartupEvent ev) throws InterruptedException {
        insertAdditionalData();
    }

    @Transactional
    void insertAdditionalData() throws InterruptedException {
        // TODO: Remove this method in production
        Auction.deleteAll();
        List<Item> items = Item.list("sold = false");
        Random random = new Random();
        int randomIndex = random.nextInt(items.size());
        Item item = items.get(randomIndex);
        Auction auction = new Auction();
        auction.setItemId(item.getId());
        auction.setActive(true);
        auction.setCurrentBid(item.getStartingPrice());
        auction.setCurrentBidder("N/A");
        auction.setAuctionEnd(System.currentTimeMillis() + 60000 * 30);
        auction.setSold(false);
        Auction.persist(auction);

        System.out.println("-------------------------------------------------");
        System.out.println("Inserting additional data...");
        System.out.println("-------------------------------------------------");
    }
}
