package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.client.LeagueEvent;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.mapper.LeagueEventReadyEventMapper;
import dev.m2t.guessers.model.*;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import dev.m2t.guessers.repository.ReadyEventRepository;
import dev.m2t.guessers.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReadyEventService {
    private final ReadyEventRepository readyEventRepository;
    private final UserRepository userRepository;
    private final LeagueEventReadyEventMapper leagueEventFootballMatchMapper;
    private final static Logger logger = LoggerFactory.getLogger(ReadyEventService.class);

    public ReadyEventService(ReadyEventRepository footballMatchRepository, UserRepository userRepository, LeagueEventReadyEventMapper leagueEventFootballMatchMapper) {
        this.readyEventRepository = footballMatchRepository;
        this.userRepository = userRepository;
        this.leagueEventFootballMatchMapper = leagueEventFootballMatchMapper;
    }

    public void saveReadyEvents(List<LeagueEvent> leagueEvents) {
        logger.info("Saving ready events.");
        int savedReadyEventsCounter = 0;
        for(LeagueEvent leagueEvent : leagueEvents) {
            ReadyEvent readyEvent = leagueEventFootballMatchMapper.toReadyEvent(leagueEvent);
            if(readyEventRepository.existsById(readyEvent.getId())) {
                logger.info("Football match already exists: {}", readyEvent.getName());
                continue;
            }

            readyEventRepository.save(readyEvent);
            savedReadyEventsCounter++;
        }
        logger.info("{} ready events saved.", savedReadyEventsCounter);
    }

    public List<ReadyEvent> getUpcomingReadyEvents(String league, String username) {
        userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

        ReadyEventLeagueEnum readyEventLeagueEnum = ReadyEventLeagueEnum.fromString(league);
        ZonedDateTime now = ZonedDateTime.now();
        return readyEventRepository.findByCommenceTimeBetweenAndLeague(now, now.plusDays(7), readyEventLeagueEnum);
    }
}
