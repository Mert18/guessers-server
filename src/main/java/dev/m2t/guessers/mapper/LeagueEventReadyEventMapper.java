package dev.m2t.guessers.mapper;

import dev.m2t.guessers.dto.client.LeagueEvent;
import dev.m2t.guessers.model.ReadyEvent;
import dev.m2t.guessers.model.ReadyEventOption;
import dev.m2t.guessers.model.ReadyEventOptionCase;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LeagueEventReadyEventMapper {
    private final Logger logger = LoggerFactory.getLogger(LeagueEventReadyEventMapper.class);

    public LeagueEventReadyEventMapper() {
    }

    public ReadyEvent toReadyEvent(LeagueEvent leagueEvent) {
        ReadyEvent readyEvent = new ReadyEvent();
        readyEvent.setId(leagueEvent.getId());
        readyEvent.setCommenceTime(leagueEvent.getCommenceTime());
        readyEvent.setName(leagueEvent.getHomeTeam() + " - " + leagueEvent.getAwayTeam());
        readyEvent.setLeague(ReadyEventLeagueEnum.fromString(leagueEvent.getSportKey()));

        if(!leagueEvent.getBookmakers().isEmpty()) {
            leagueEvent.getBookmakers().get(0).getMarkets().forEach(market -> {
                ReadyEventOption readyEventOption = new ReadyEventOption();
                readyEventOption.setId(leagueEvent.getId() + "-" + market.getKey());
                readyEventOption.setName(market.getKey());

                market.getOutcomes().forEach(outcome -> {
                    ReadyEventOptionCase readyEventOptionCase = new ReadyEventOptionCase();
                    readyEventOptionCase.setName(outcome.getName());
                    readyEventOptionCase.setOdds(outcome.getPrice());
                    readyEventOption.addReadyEventOptionCase(readyEventOptionCase);
                });

                readyEvent.addReadyEventOption(readyEventOption);
            });

            logger.info("Ready event created: {}", readyEvent.getName());
            return readyEvent;
        }else {
            logger.error("No bookmakers found for event: {}", leagueEvent.getId());
            return null;
        }
    }


}
