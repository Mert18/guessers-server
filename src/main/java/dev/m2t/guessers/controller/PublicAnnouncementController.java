package dev.m2t.guessers.controller;

import dev.m2t.guessers.model.PublicAnnouncement;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PublicAnnouncementController {
    private final SimpMessagingTemplate messagingTemplate;

    public PublicAnnouncementController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Broadcasts an announcement to all connected public websocket clients.
     * Clients should subscribe to /topic/announcements to receive messages.
     */
    public void broadcastAnnouncement(PublicAnnouncement announcement) {
        messagingTemplate.convertAndSend("/topic/announcements", announcement);
    }
}
