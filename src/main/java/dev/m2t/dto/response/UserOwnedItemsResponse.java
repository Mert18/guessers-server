package dev.m2t.dto.response;

import dev.m2t.model.Item;

import java.util.List;

public class UserOwnedItemsResponse {
    private List<Item> items;

    public UserOwnedItemsResponse() {
    }

    public UserOwnedItemsResponse(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
