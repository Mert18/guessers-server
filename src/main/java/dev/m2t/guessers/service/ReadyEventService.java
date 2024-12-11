package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.client.nosyapi.LeagueEvent;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.mapper.LeagueEventReadyEventMapper;
import dev.m2t.guessers.model.*;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import dev.m2t.guessers.repository.ReadyEventRepository;
import dev.m2t.guessers.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    public void saveReadyEvent(LeagueEvent leagueEvent, ReadyEventLeagueEnum league) {
        logger.info("Saving ready events for league {}.", league.getTextEn());
        ReadyEvent readyEvent = leagueEventFootballMatchMapper.toReadyEvent(leagueEvent, league);
        if(readyEventRepository.existsById(readyEvent.getId())) {
            logger.info("Football match already exists: {}", readyEvent.getName());
        }

        readyEventRepository.save(readyEvent);
        logger.info("League event saved: {}", leagueEvent.getTeams());
    }

    public List<ReadyEvent> getUpcomingReadyEvents(String league, String username) {
        ReadyEventLeagueEnum rele = ReadyEventLeagueEnum.fromTr(league);
        if(!rele.equals(ReadyEventLeagueEnum.UNKNOWN)) {
            userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

            ZonedDateTime now = ZonedDateTime.now();
            return readyEventRepository.findByCommenceTimeBetweenAndLeague(now, now.plusDays(1), rele);
        }else {
            logger.warn("Fetch ready events failed because league {} is not defined in enum.", league);
        }
        return new ArrayList<>();
    }
}
