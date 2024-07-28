package dev.m2t.unlucky.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoredImageRepository extends MongoRepository<StoredImage, String> {
}
