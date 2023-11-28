package dev.m2t;

import io.quarkus.logging.Log;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/auction")
public class AuctionWebSocket {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private Jsonb jsonb = JsonbBuilder.create();

    public static class BidMessage {
        public Long auctionId;
        public Double bid;
        public String bidderId;
    }

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
            BidMessage bidMessage = jsonb.fromJson(message, BidMessage.class);

            // Validate the bid
            if (!isValidBid(bidMessage)) {
                // Handle invalid bid scenario
                // E.g., send an error message back to the bidder
                session.getBasicRemote().sendText("Invalid bid");
                return;
            }

            // Update auction state and database
            updateAuctionState(bidMessage);

            // Broadcast the updated bid to all clients
            String updatedBidMessage = jsonb.toJson(bidMessage);
            broadcastToAll(updatedBidMessage);
        } catch (Exception e) {
            // Handle exceptions, e.g., logging, sending error messages to clients
        }
    }

    private boolean isValidBid(BidMessage bidMessage) {
        // Implement validation logic
        // E.g., check if the bid is higher than the current bid, auction is still open, etc.
        return true; // Placeholder for actual validation logic
    }

    private void updateAuctionState(BidMessage bidMessage) {
        // Implement auction state update logic
        // This would involve interacting with your database/entities to update the current bid
        // Remember to handle concurrency issues if multiple bids arrive closely together
    }

    private void broadcastToAll(String message) throws IOException {
        for (Session s : sessions) {
            s.getBasicRemote().sendText(message);
        }
    }
}