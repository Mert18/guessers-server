package dev.m2t.service;

import dev.m2t.model.Auction;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class AuctionService {

    public List<Auction> getAllAuctions() {
        return Auction.listAll();
    }
}
