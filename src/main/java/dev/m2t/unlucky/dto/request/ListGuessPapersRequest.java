package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.enums.GuessPaperStatusEnum;

import java.util.List;

public class ListGuessPapersRequest {
    private List<GuessPaperStatusEnum> statuses;

    public ListGuessPapersRequest() {
    }

    public ListGuessPapersRequest(List<GuessPaperStatusEnum> statuses) {
        this.statuses = statuses;
    }

    public List<GuessPaperStatusEnum> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<GuessPaperStatusEnum> statuses) {
        this.statuses = statuses;
    }
}
