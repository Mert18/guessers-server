package dev.m2t.websocket;

import dev.m2t.exception.InvalidBidException;
import dev.m2t.model.Auction;
import dev.m2t.model.Bid;
import dev.m2t.service.AuctionService;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;

@ServerEndpoint("/auction")
public class AuctionWebSocket {
    @Inject
    AuctionService auctionService;

    @Inject
    ExecutorService executorService; // Managed executor service

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private Jsonb jsonb = JsonbBuilder.create();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        Log.info("New session opened: " + session.getId());
        sessions.add(session);

        Auction activeAuction = auctionService.getActiveAuction();
        session.getBasicRemote().sendText("{\"type\": \"auctionUpdate\", \"data\": " + jsonb.toJson(activeAuction) + "}");
    }

    @OnClose
    public void onClose(Session session) {
        Log.info("Session closed: " + session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Bid bidMessage = jsonb.fromJson(message, Bid.class);

        Log.info("Bid received:" + bidMessage.getBidder() + " " + bidMessage.getBid() + " " + bidMessage.getItemId() + " " + bidMessage.getAuctionId());
        executorService.execute(() -> {
            try {
                bidValidator(bidMessage); // This will throw an exception if the bid is invalid

                updateAuctionState(bidMessage); // Update auction state and database

                // Broadcast the updated bid to all clients
                String updatedBidMessage = "{\"type\": \"bidUpdate\", \"data\": " + jsonb.toJson(bidMessage) + "}";
                broadcastToAll(updatedBidMessage);
            } catch (InvalidBidException e) {
                try {
                    session.getBasicRemote().sendText("{\"type\": \"invalidBid\"}");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }catch (Exception e) {
                Log.error("Manual assigned thread received exception: " + e.getMessage());
            }
        });
    }

    private void updateAuctionState(Bid bidMessage) {
        auctionService.handleReceivedBid(bidMessage);
    }

    public void broadcastToAll(String message) throws IOException {
        for (Session s : sessions) {
            s.getBasicRemote().sendText(message);
        }
    }

    public void bidValidator(Bid bid) throws IOException {
        List<Bid> bids = Bid.find("auctionId = ?1 and itemId = ?2", bid.getAuctionId(), bid.getItemId()).list();
        Log.info("How many bids: " + bids.size());
        if(bids.isEmpty()) {
            Log.info("The incoming bid is valid.");
            bid.persist();
        }else {
            Optional<Bid> highestBid = bids.stream().max(Comparator.comparing(Bid::getBid));
            Log.info("Highest bid: " + highestBid.get());

            if (bid.getAuctionId() == null || bid.getItemId() == null || bid.getBidder() == null || bid.getBid() == null) {
                Log.info("Invalid bid received, bid is null.");
                throw new RuntimeException("Bid must contain auctionId, itemId, bidder, and bid");
            } else if (highestBid.isPresent() && bid.getBid() <= highestBid.get().getBid()) {
                Log.info("Invalid bid received, bid is lower than current highest bid.");
                throw new InvalidBidException("Bid must be higher than the current highest bid.");
            }else {
                Log.info("The incoming bid is valid.");
                bid.persist();
            }
        }
    }
}