package dev.m2t.guessers.mapper;

import dev.m2t.guessers.dto.client.nosyapi.Bet;
import dev.m2t.guessers.dto.client.nosyapi.BetOdd;
import dev.m2t.guessers.dto.client.nosyapi.LeagueEvent;
import dev.m2t.guessers.model.ReadyEvent;
import dev.m2t.guessers.model.ReadyEventOption;
import dev.m2t.guessers.model.ReadyEventOptionCase;
import dev.m2t.guessers.model.enums.MatchOptionNameTranslatorEnum;
import dev.m2t.guessers.model.enums.ReadyEventLeagueEnum;
import dev.m2t.guessers.util.ReadyEventGuessOptionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LeagueEventReadyEventMapper {
    private final ReadyEventGuessOptionTranslator readyEventGuessOptionTranslator;
    private final Logger logger = LoggerFactory.getLogger(LeagueEventReadyEventMapper.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LeagueEventReadyEventMapper(ReadyEventGuessOptionTranslator readyEventGuessOptionTranslator) {
        this.readyEventGuessOptionTranslator = readyEventGuessOptionTranslator;
    }

    public ReadyEvent toReadyEvent(LeagueEvent leagueEvent, ReadyEventLeagueEnum league) {
        ReadyEvent readyEvent = new ReadyEvent();
        readyEvent.setId(String.valueOf(leagueEvent.getMatchId()));
        ZoneId istanbulZone = ZoneId.of("Europe/Istanbul");
        LocalDateTime localDateTime = LocalDateTime.parse(leagueEvent.getDateTime(), formatter);
        ZonedDateTime istanbulDateTime = localDateTime.atZone(istanbulZone);
        ZonedDateTime utcDateTime = istanbulDateTime.withZoneSameInstant(ZoneOffset.UTC);

        readyEvent.setCommenceTime(utcDateTime);
        readyEvent.setName(leagueEvent.getTeams());
        readyEvent.setLeague(league);

        for(Bet bet : leagueEvent.getBets()) {
            if(bet.getType().equals("Oyuncu") ||
                    bet.getType().equals("Goller,Oyuncu") ||
                    bet.getType().equals("Korner/Kart") ||
                    bet.getType().equals("Diğer") ||
                    bet.getType().equals("Kim Kazanır,Korner/Kart") ||
                    bet.getType().equals("Alt/Üst,Korner/Kart") ||
                    bet.getType().equals("Özel") ||
                    bet.getType().equals("Skor")
            ) {
                continue;
            }
            MatchOptionNameTranslatorEnum mote = MatchOptionNameTranslatorEnum.fromTextTr(bet.getGameName());

            if(!mote.equals(MatchOptionNameTranslatorEnum.UNKNOWN)) {
                ReadyEventOption readyEventOption = getReadyEventOption(bet, mote);
                if(readyEventOption != null) {
                    readyEvent.addReadyEventOption(readyEventOption);
                }
            }
        }

        logger.info("Ready event created: {}", readyEvent.getName());
        return readyEvent;
    }

    private static ReadyEventOption getReadyEventOption(Bet bet, MatchOptionNameTranslatorEnum mote) {
        ReadyEventOption readyEventOption = new ReadyEventOption();
        readyEventOption.setId(bet.getMatchId() + "_" + bet.getGameId());
        readyEventOption.setName(mote.getTextEn());
        readyEventOption.setPrecedence(mote.getCode());

        if(bet.getOdds() != null && !bet.getOdds().isEmpty()) {
            for(BetOdd bo: bet.getOdds()) {
                ReadyEventOptionCase readyEventOptionCase = new ReadyEventOptionCase();
                readyEventOptionCase.setName(bo.getValue());
                readyEventOptionCase.setOdds(bo.getOdd());
                readyEventOption.addReadyEventOptionCase(readyEventOptionCase);
            }
            return readyEventOption;
        }
        return null;
    }


}
