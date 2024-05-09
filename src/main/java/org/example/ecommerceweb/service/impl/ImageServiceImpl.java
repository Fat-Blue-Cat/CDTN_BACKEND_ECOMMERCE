package org.example.ecommerceweb.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.domains.Image;
import org.example.ecommerceweb.repository.ImageRepository;
import org.example.ecommerceweb.service.CloudinaryService;
import org.example.ecommerceweb.service.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    @Override
    public List<Image> list(){
        return imageRepository.findByOrderById();
    }
    @Override
    public Optional<Image> getOne(int id){
        return imageRepository.findById(id);
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void delete(int id) throws IOException {
        Image image = imageRepository.findById(id).get();
        cloudinaryService.delete(image.getImageId());
        imageRepository.deleteById(id);
    }
    @Override
    public boolean exists(int id){
        return imageRepository.existsById(id);
    }
}
