package dev.m2t.guessers.model.enums;

public enum ReadyEventLeagueEnum {
    TURKISH_SUPER_LEAGUE("soccer_turkey_super_league"),
    ENGLISH_PREMIER_LEAGUE("soccer_epl");

    private final String key;

    ReadyEventLeagueEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static ReadyEventLeagueEnum fromString(String league) {
        for(ReadyEventLeagueEnum readyEventLeagueEnum : ReadyEventLeagueEnum.values()) {
            if(readyEventLeagueEnum.getKey().equals(league)) {
                return readyEventLeagueEnum;
            }
        }
        return null;
    }
}
