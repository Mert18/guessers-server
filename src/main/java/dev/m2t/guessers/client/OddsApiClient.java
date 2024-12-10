package dev.m2t.guessers.client;

import dev.m2t.guessers.dto.client.nosyapi.LeagueEvent;
import dev.m2t.guessers.service.ReadyEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class OddsApiClient {

    private static final Logger logger = LoggerFactory.getLogger(OddsApiClient.class);
    private final RestClient defaultClient;
    private final ReadyEventService footballMatchService;

    public OddsApiClient(ReadyEventService footballMatchService) {
        this.footballMatchService = footballMatchService;
        this.defaultClient = RestClient.create();
    }

    @Value("${oddsApi.key}")
    private String apiKey;

//    public void fetchOddsLeague(String league) {
//        logger.info("Fetching odds for league: {}", league);
//        ResponseEntity<List<LeagueEvent>> response = defaultClient.get()
//                .uri("https://www.nosyapi.com/apiv2/service/bettable-matches?league=" + league + "&apiKey=" + apiKey)
//                .retrieve()
//                .toEntity(new ParameterizedTypeReference<List<LeagueEvent>>() {});
//
//        logger.info("Fetched {} events", response.getBody().size());
//        footballMatchService.saveReadyEvents(response.getBody());
//    }
}
