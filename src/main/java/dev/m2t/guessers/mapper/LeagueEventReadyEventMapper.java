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
            int maxMarketsIndex = 0;
            for(int i=0; i<leagueEvent.getBookmakers().size(); i++) {
                if(leagueEvent.getBookmakers().get(i).getMarkets().size() > leagueEvent.getBookmakers().get(maxMarketsIndex).getMarkets().size()) {
                    maxMarketsIndex = i;
                }
            }

            leagueEvent.getBookmakers().get(maxMarketsIndex).getMarkets().forEach(market -> {
                ReadyEventOption readyEventOption = new ReadyEventOption();
                readyEventOption.setId(leagueEvent.getId() + "-" + market.getKey());
                switch (market.getKey()){
                    case "h2h":
                        readyEventOption.setName("Match Result");
                        break;
                    case "totals":
                        readyEventOption.setName("Over/Under");
                        break;
                    case "spreads":
                        readyEventOption.setName("Spread");
                        break;
                    default:
                        readyEventOption.setName(market.getKey());
                        break;
                }

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
