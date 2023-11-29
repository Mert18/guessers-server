package dev.m2t;

import dev.m2t.model.Bid;
import dev.m2t.service.AuctionService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@ServerEndpoint("/auction")
public class AuctionWebSocket {
    @Inject
    AuctionService auctionService;

    @Inject
    ExecutorService executorService; // Managed executor service

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private Jsonb jsonb = JsonbBuilder.create();

    @OnOpen
    public void onOpen(Session session) {
        Log.info("New session opened: " + session.getId());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        Log.info("Session closed: " + session.getId());
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Log.info("Message received: " + message);
        try {
            Bid bidMessage = jsonb.fromJson(message, Bid.class);
            Log.info("Bid received: " + bidMessage.getBid());
            executorService.execute(() -> {
                try {
                    bidValidator(bidMessage, session); // This will throw an exception if the bid is invalid

                    updateAuctionState(bidMessage); // Update auction state and database

                    // Broadcast the updated bid to all clients
                    String updatedBidMessage = jsonb.toJson(bidMessage);
                    broadcastToAll(updatedBidMessage);
                } catch (Exception e) {
                    Log.error("Manual assigned thread received exception: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.error("Ws method exception: " + e.getMessage());
        }
    }

    private void updateAuctionState(Bid bidMessage) {
        auctionService.handleReceivedBid(bidMessage);
    }

    private void broadcastToAll(String message) throws IOException {
        for (Session s : sessions) {
            s.getBasicRemote().sendText(message);
        }
    }

    @Transactional
    public void bidValidator(Bid bid, Session session) throws IOException {
        List<Bid> bids = Bid.list("SELECT b FROM Bid b WHERE b.auctionId = ?1 and b.itemId = ?2 ORDER BY b.bid DESC", bid.getAuctionId(), bid.getItemId());
        Log.info("There are " + bids.size() + " bids");
        Optional<Bid> highestBid = bids.stream().findFirst();

        if (highestBid.isEmpty()) {
            Log.info("No bids received yet, the incoming bid is valid.");
        } else if (bid.getAuctionId() == null || bid.getItemId() == null || bid.getBidder() == null || bid.getBid() == null) {
            Log.info("Invalid bid received, bid is null.");
            session.getBasicRemote().sendText("Invalid bid");
            throw new RuntimeException("Bid must contain auctionId, itemId, bidder, and bid");
        } else if (highestBid.isPresent() && bid.getBid() <= highestBid.get().getBid()) {
            Log.info("Invalid bid received, bid is lower than current highest bid.");
            session.getBasicRemote().sendText("Invalid bid");
            throw new RuntimeException("Bid must be higher than the current highest bid");
        }
    }
}