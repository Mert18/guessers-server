package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.exception.ResourceNotFoundException;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.model.GuessPaper;
import dev.m2t.guessers.model.SharedGuessPaper;
import dev.m2t.guessers.repository.GuessPaperRepository;
import dev.m2t.guessers.repository.SharedGuessPaperRepository;
import dev.m2t.guessers.util.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
public class SharedGuessPaperService {
    private final SharedGuessPaperRepository sharedGuessPaperRepository;
    private final GuessPaperRepository guessPaperRepository;
    private static final Logger logger = LoggerFactory.getLogger(SharedGuessPaperService.class);

    public SharedGuessPaperService(SharedGuessPaperRepository sharedGuessPaperRepository, GuessPaperRepository guessPaperRepository) {
        this.sharedGuessPaperRepository = sharedGuessPaperRepository;
        this.guessPaperRepository = guessPaperRepository;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanupExpiredGuessPapers() {
        logger.info("Cleaning up the expired shared guess papers...");
        sharedGuessPaperRepository.deleteExpiredGuessPapers();
        logger.info("Expired guess papers cleaned up.");
    }


    public BaseResponse shareGuessPaper(String username, Long guessPaperId) {
        logger.info("{} sharing guess paper {}", username, guessPaperId);

        GuessPaper guessPaper = guessPaperRepository.findById(guessPaperId).orElseThrow(() -> new ResourceNotFoundException("GuessPaper", "id", guessPaperId));
        if(!guessPaper.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You are not the owner of this guess paper. Thus you cannot share it.");
        }

        // Check if already exists
        String token = saveSharedGuessPaper(guessPaper);

        return new BaseResponse("Guess paper is shared successfully.", true, false, token);
    }

    public String saveSharedGuessPaper(GuessPaper guessPaper) {
        logger.info("Saving shared guess paper: {}", guessPaper.getId());
        SharedGuessPaper sgp = new SharedGuessPaper();
        sgp.setGuessPaper(guessPaper);
        sgp.setToken(RandomStringGenerator.generateRandomString());
        sgp.setExpiryTime(new Timestamp(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48))); // Set TTL of 1 hour

        SharedGuessPaper savedSharedGuessPaper = sharedGuessPaperRepository.save(sgp);

        logger.info("Guess paper {} shared.", guessPaper.getId());
        return savedSharedGuessPaper.getToken();
    }

    public BaseResponse getSharedGuessPaper(String token) {
        SharedGuessPaper sgp = sharedGuessPaperRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Shared guess paper", "token", token));
        return new BaseResponse("Shared guess paper is found.", true, false, sgp);
    }
}
