package dev.m2t.guessers.model.enums;

public enum ReadyEventLeagueEnum {
    UNKNOWN(-1, "Unknown", "Bilinmiyor"),
    UEFA_CHAMPIONS_LEAGUE(0, "UEFA Champions League", "UEFA Şampiyonlar Ligi"),
    UEFA_EUROPE_LEAGUE(1, "UEFA Europe League", "UEFA Avrupa Ligi"),
    SUPER_LEAGUE(2, "Turkish Super League", "Türkiye Süper Lig"),
    PREMIER_LEAGUE(3, "English Premier League", "İngiltere Premier Lig"),
    LA_LIGA(4, "La Liga", "İspanya La Liga");

    private final int code;
    private final String textEn;
    private final String textTr;

    ReadyEventLeagueEnum(int code, String textEn, String textTr) {
        this.code = code;
        this.textEn = textEn;
        this.textTr = textTr;
    }

    public static ReadyEventLeagueEnum fromCode(int code) {
        for(ReadyEventLeagueEnum rele: ReadyEventLeagueEnum.values()) {
            if(rele.code == code) {
                return rele;
            }
        }
        return ReadyEventLeagueEnum.UNKNOWN;
    }

    public static ReadyEventLeagueEnum fromTr(String textTr) {
        for(ReadyEventLeagueEnum rele: ReadyEventLeagueEnum.values()) {
            if(rele.textTr.equals(textTr)) {
                return rele;
            }
        }
        return ReadyEventLeagueEnum.UNKNOWN;
    }

    public static ReadyEventLeagueEnum fromEn(String textEn) {
        for(ReadyEventLeagueEnum rele: ReadyEventLeagueEnum.values()) {
            if(rele.textEn == textEn) {
                return rele;
            }
        }
        return ReadyEventLeagueEnum.UNKNOWN;
    }

    public int getCode() {
        return this.code;
    }

    public String getTextEn() {
        return this.textEn;
    }

    public String getTextTr() {
        return this.textTr;
    }

}
