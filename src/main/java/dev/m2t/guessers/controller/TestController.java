package dev.m2t.guessers.controller;

import dev.m2t.guessers.client.OddsApiClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {

    private final OddsApiClient oddsApiClient;

    public TestController(OddsApiClient oddsApiClient) {
        this.oddsApiClient = oddsApiClient;
    }

    @GetMapping("/leagueodds")
    public void test() {
        String league = "soccer_epl";
        oddsApiClient.fetchOddsLeague(league);
    }
}
