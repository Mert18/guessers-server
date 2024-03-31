package dev.m2t.util;

import dev.m2t.model.Auction;
import dev.m2t.model.Item;
import dev.m2t.service.AuctionService;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class DataInitializer {
    @Inject
    AuctionService auctionService;

    void onStart(@Observes StartupEvent ev) throws InterruptedException, IOException {
        auctionService.handleNewAuction();
    }
}