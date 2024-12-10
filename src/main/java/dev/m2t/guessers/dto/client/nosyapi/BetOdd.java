package dev.m2t.guessers.dto.client.nosyapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BetOdd {
    @JsonProperty("odd")
    private Double odd;

    @JsonProperty("value")
    private String value;

    public BetOdd() {

    }

    public BetOdd(Double odd, String value) {
        this.odd = odd;
        this.value = value;
    }

    public Double getOdd() {
        return odd;
    }

    public void setOdd(Double odd) {
        this.odd = odd;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
