package dev.m2t.guessers.model.enums;

public enum MatchOptionNameTranslatorEnum {
    UNKNOWN(0, "Bilinmiyor", "Unknown"),
    MORE_CARDS(1, "Maç Sonucu", "Match Result"),
    TOTAL_GOALS(2, "Toplam Gol", "Total Goals"),
    DOUBLE_CHANCE(3, "Çifte Şans", "Double Chance"),
    FIRST_SECOND_HALF_RESULT(4, "İlk Yarı / Maç Sonucu", "Half-Time / Full-Time Result"),
    UNDER_OVER_3_5(5, "Alt/Üst 3.5", "Over/Under 3.5"),
    UNDER_OVER_1_5(6, "Alt/Üst 1.5", "Over/Under 1.5"),
    UNDER_OVER_2_5(7, "Alt/Üst 2.5", "Over/Under 2.5"),
    BOTH_TEAMS_TO_SCORE(8, "Karşılıklı Gol", "Both Teams To Score"),
    MATCH_SCORE(9, "Maç Skoru", "Match Score"),
    FIRST_HALF_BTSS(10, "İlk Yarı Karşılıklı Gol", "First Half Both Teams To Score"),
    FIRST_GOAL(11, "İlk Golü Hangi Takım Atar", "Which Team Scores First"),
    PLAYER_SCORES(12, "Oyuncu Gol Atar", "Player To Score"),
    RED_CARD(13, "Kırmızı Kart Olur Mu", "Will There Be A Red Card"),
    PENALTY(14, "Penaltı Olur mu?", "Will There Be A Penalty");

    private int code;
    private String textTr;
    private String textEn;

    MatchOptionNameTranslatorEnum() {

    }

    MatchOptionNameTranslatorEnum(int code, String textTr, String textEn) {
        this.code = code;
        this.textTr = textTr;
        this.textEn = textEn;
    }

    public static MatchOptionNameTranslatorEnum fromCode(int code) {
        for(MatchOptionNameTranslatorEnum mote: MatchOptionNameTranslatorEnum.values()) {
            if(mote.code == code) {
                return mote;
            }
        }
        return MatchOptionNameTranslatorEnum.UNKNOWN;
    }

    public static MatchOptionNameTranslatorEnum fromTextTr(String textTr) {
        for(MatchOptionNameTranslatorEnum mote: MatchOptionNameTranslatorEnum.values()) {
            if(mote.textTr.equals(textTr)) {
                return mote;
            }
        }
        return MatchOptionNameTranslatorEnum.UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public String getTextTr() {
        return textTr;
    }

    public String getTextEn() {
        return textEn;
    }
}
