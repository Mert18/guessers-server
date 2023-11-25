package dev.m2t.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Cacheable
public class Item extends PanacheEntity {
   @Column(length = 80, unique = true, nullable = false)
   public String name;

   @Column(nullable = false)
   public Double price;

   @Column(nullable = false)
   public boolean sold;

   @Column(nullable = false)
   public boolean startingPrice;
}
