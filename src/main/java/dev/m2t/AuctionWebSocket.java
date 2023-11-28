package dev.m2t;

import dev.m2t.model.Bid;
import dev.m2t.service.AuctionService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
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
    @Inject
    AuctionService auctionService;
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
            Bid.validator(bidMessage, session); // This will throw an exception if the bid is invalid

            // Update auction state and database
            updateAuctionState(bidMessage);

            // Broadcast the updated bid to all clients
            String updatedBidMessage = jsonb.toJson(bidMessage);
            broadcastToAll(updatedBidMessage);
        } catch (Exception e) {
            // Handle exceptions, e.g., logging, sending error messages to clients
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
}