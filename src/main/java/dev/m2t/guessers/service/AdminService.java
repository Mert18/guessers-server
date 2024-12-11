package dev.m2t.guessers.service;

import dev.m2t.guessers.client.OddsApiClient;
import dev.m2t.guessers.dto.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final OddsApiClient oddsApiClient;

    public AdminService(OddsApiClient oddsApiClient) {
        this.oddsApiClient = oddsApiClient;
    }

    public BaseResponse fetchReadyEventsFootball(String league) throws Exception {
        oddsApiClient.fetchOddsLeague(league);
        return new BaseResponse("Ready events fetched successfully", true, false);
    }
}
