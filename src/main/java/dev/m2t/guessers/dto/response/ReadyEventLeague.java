package dev.m2t.guessers.dto.response;

public class ReadyEventLeague {
    private Integer code;
    private String name;

    public ReadyEventLeague() {

    }
    public ReadyEventLeague(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
