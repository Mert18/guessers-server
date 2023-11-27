package dev.m2t.dto.request;

public class UpdateItemRequest {
    private Long id;
    private GenerateItemRequest item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenerateItemRequest getItem() {
        return item;
    }

    public void setItem(GenerateItemRequest item) {
        this.item = item;
    }
}
