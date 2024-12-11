package dev.m2t.guessers.client;

import dev.m2t.guessers.dto.client.nosyapi.LeagueEvent;
import dev.m2t.guessers.dto.client.nosyapi.LeagueEventDetailResponse;
import dev.m2t.guessers.dto.client.nosyapi.MatchesResponse;
import dev.m2t.guessers.dto.client.nosyapi.SingleMatch;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import dev.m2t.guessers.service.ReadyEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class OddsApiClient {

    private static final Logger logger = LoggerFactory.getLogger(OddsApiClient.class);
    private final RestClient defaultClient;
    private final ReadyEventService readyEventService;

    public OddsApiClient(ReadyEventService readyEventService) {
        this.readyEventService = readyEventService;
        this.defaultClient = RestClient.create();
    }

    @Value("${oddsApi.key}")
    private String apiKey;

    public void fetchOddsLeague(ReadyEventLeagueEnum league) throws Exception {
        logger.info("Fetching odds for league: {}", league.getTextEn());
        ResponseEntity<MatchesResponse> response = defaultClient.get()
            .uri("https://www.nosyapi.com/apiv2/service/bettable-matches?type=1&league=" + league.getTextTr() + "&apiKey=" + apiKey)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<>() {
            });

        if(response.hasBody() && response.getBody() != null) {
            for(SingleMatch sm: response.getBody().getData()) {
                ResponseEntity<LeagueEventDetailResponse> responseMatchDetail = defaultClient.get()
                        .uri("https://www.nosyapi.com/apiv2/service/bettable-matches/details?matchID=" + sm.getMatchId() + "&apiKey=" + apiKey)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<>() {
                        });

                if(responseMatchDetail.hasBody() && responseMatchDetail.getBody() != null && responseMatchDetail.getBody().getData() != null && !responseMatchDetail.getBody().getData().isEmpty()) {
                    readyEventService.saveReadyEvent(responseMatchDetail.getBody().getData().get(0), league);
                }else {
                    logger.warn("No match found for league: {} and matchId: {}", league.getTextEn(), sm.getMatchId());
                }
            }
        }else {
            logger.error("No response returned from api response.");
            throw new Exception("API Response error");
        }

        logger.info("Fetched {} events", response.getBody());
    }
}
