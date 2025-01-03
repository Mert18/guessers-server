package dev.m2t.guessers.service;

import dev.m2t.guessers.client.OddsApiClient;
import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.response.ReadyEventLeague;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final OddsApiClient oddsApiClient;
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(OddsApiClient oddsApiClient) {
        this.oddsApiClient = oddsApiClient;
    }

    public BaseResponse fetchReadyEventsFootball(Integer league) throws Exception {
        ReadyEventLeagueEnum rele = ReadyEventLeagueEnum.fromCode(league);

        if(!rele.equals(ReadyEventLeagueEnum.UNKNOWN)) {
            oddsApiClient.fetchOddsLeague(rele);
        }else {
            logger.warn("Fetch ready events failed because league {} is not defined in enum.", league);
        }
        return new BaseResponse("Ready events fetched successfully", true, false);
    }

    public BaseResponse fetchReadyEventLeagues() {
        List<ReadyEventLeague> rell = new ArrayList<>();
        for(ReadyEventLeagueEnum rele: ReadyEventLeagueEnum.values()) {
            if(rele.getCode() != -1) {
                rell.add(new ReadyEventLeague(rele.getCode(), rele.getTextEn()));
            }
        }

        return new BaseResponse("Ready event leagues fetched.", true, false, rell);
    }
}
