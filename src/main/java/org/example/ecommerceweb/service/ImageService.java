package org.example.ecommerceweb.service;

import org.example.ecommerceweb.domains.Image;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
     List<Image> list();
     Optional<Image> getOne(int id);
     void save(Image image);
     void delete(int id) throws IOException;
     boolean exists(int id);
}
