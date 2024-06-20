package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.request.CreateStoredImageRequest;
import dev.m2t.unlucky.model.StoredImage;
import dev.m2t.unlucky.repository.StoredImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StoredImageService {
    private final StoredImageRepository storedImageRepository;

    public StoredImageService(StoredImageRepository storedImageRepository) {
        this.storedImageRepository = storedImageRepository;
    }

    public Page<StoredImage> findAllByPage(Pageable pageable) {
        return storedImageRepository.findAll(pageable);
    }

    public StoredImage createStoredImage(CreateStoredImageRequest request) {
        StoredImage storedImage = new StoredImage();
        storedImage.setUrl(request.getUrl());
        storedImage.setDescription(request.getDescription());
        storedImage.setDate(request.getDate());
        storedImage.setUploadedAt(LocalDateTime.now());
        return storedImageRepository.save(storedImage);
    }
}
