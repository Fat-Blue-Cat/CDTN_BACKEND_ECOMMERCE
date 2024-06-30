package org.example.ecommerceweb.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.service.CategoryService;
import org.example.ecommerceweb.service.CloudinaryService;
import org.example.ecommerceweb.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/admin/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CloudinaryService cloudinaryService;

    private final ImageService imageService;

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "", required = false) String name, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "5") int size) {
        try {
            return new ResponseEntity<>(categoryService.getAllCategories(name,page,size), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        try {

            return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(required = false) MultipartFile multipartFile, @RequestParam String name) throws IOException {
        try{
            if(multipartFile.isEmpty()){
                multipartFile = null;
            }
            return new ResponseEntity<>( categoryService.createCategory(multipartFile,name), HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestParam(required = false) MultipartFile multipartFile, @RequestParam String name, @PathVariable("id") Long id){
        if (multipartFile == null || multipartFile.isEmpty()) {
            multipartFile = null; // or handle it in a way that suits your needs
        }
        try {

            return new ResponseEntity<>(categoryService.updateCategory(id,multipartFile, name), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to update category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            categoryService.deleteCategory( id);
            return new ResponseEntity<>("Delete category success! ", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to delete category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
