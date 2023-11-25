package dev.m2t.repository;

import dev.m2t.model.Item;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

public interface ItemRepository extends PanacheRepository<Item> {

}
