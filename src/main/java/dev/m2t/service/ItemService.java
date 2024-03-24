package dev.m2t.service;

import dev.m2t.dto.request.GenerateItemRequest;
import dev.m2t.dto.request.UpdateItemRequest;
import dev.m2t.model.Item;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ApplicationScoped
public class ItemService {

    public Item createItem(GenerateItemRequest generateItemRequest) {
        Item item = new Item();
        item.setName(generateItemRequest.getName());
        item.setStartingPrice(generateItemRequest.getStartingPrice());
        item.setPhotoUrl(generateItemRequest.getPhotoUrl());
        item.setSold(false);

        item.persist();
        return item;
    }

    public Item updateItem(UpdateItemRequest updateItemRequest) throws IllegalAccessException, InvocationTargetException {
        Item item = Item.findById(updateItemRequest.getId());

        GenerateItemRequest genItemRequest = updateItemRequest.getItem();

        // Using reflection to update the item.
        // Not the best jpa-friendly approach but it works. I wanted to avoid repetitive non-null checks.
        if (genItemRequest != null) {
            for (Field field : GenerateItemRequest.class.getDeclaredFields()) {
                field.setAccessible(true); // Access private fields
                Object value = field.get(genItemRequest);

                if (value != null) {
                    String fieldName = field.getName();
                    String methodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    try {
                        Method method = Item.class.getMethod(methodName, field.getType());
                        method.invoke(item, value);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        Log.error("Error updating item field: " + fieldName, e);
                    }
                }
            }
        }

        item.persist();
        return item;
    }
}
