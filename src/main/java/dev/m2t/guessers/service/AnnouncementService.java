package dev.m2t.guessers.service;

import dev.m2t.guessers.controller.PublicAnnouncementController;
import dev.m2t.guessers.model.PublicAnnouncement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnnouncementService {
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementService.class);
    private final PublicAnnouncementController announcementController;

    public AnnouncementService(PublicAnnouncementController announcementController) {
        this.announcementController = announcementController;
    }

    /**
     * Sends a public announcement to all connected websocket clients.
     *
     * @param type The type of announcement (e.g., "INFO", "WARNING", "SUCCESS", "ERROR")
     * @param title The title of the announcement
     * @param message The announcement message content
     */
    public void sendAnnouncement(String type, String title, String message) {
        PublicAnnouncement announcement = new PublicAnnouncement(type, title, message);
        announcement.setId(UUID.randomUUID().toString());

        logger.info("Broadcasting public announcement: [{}] {} - {}", type, title, message);
        announcementController.broadcastAnnouncement(announcement);
    }

    /**
     * Sends an info announcement.
     */
    public void sendInfo(String title, String message) {
        sendAnnouncement("INFO", title, message);
    }

    /**
     * Sends a warning announcement.
     */
    public void sendWarning(String title, String message) {
        sendAnnouncement("WARNING", title, message);
    }

    /**
     * Sends a success announcement.
     */
    public void sendSuccess(String title, String message) {
        sendAnnouncement("SUCCESS", title, message);
    }

    /**
     * Sends an error announcement.
     */
    public void sendError(String title, String message) {
        sendAnnouncement("ERROR", title, message);
    }
}
