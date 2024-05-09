package org.example.ecommerceweb.controller.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.dto.req.product.CreateProductRequest;
import org.example.ecommerceweb.dto.req.product.OptionRequestDto;
import org.example.ecommerceweb.dto.req.product.OptionSelectRequestDto;
import org.example.ecommerceweb.dto.req.product.VariantsRequestDto;
import org.example.ecommerceweb.mapper.Mapstruct;
import org.example.ecommerceweb.repository.ProductRepository;
import org.example.ecommerceweb.service.CloudinaryService;
//import org.example.ecommerceweb.service.ProductService;
import org.example.ecommerceweb.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute CreateProductRequest createProductRequest, @RequestParam(required = false) MultipartFile[] multipartFiles) throws IOException {
       try {


           productService.createProduct(createProductRequest, multipartFiles);
           return new ResponseEntity<>("Add product success", HttpStatus.OK);
       }catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable long id){
        try {
            return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/options/{optionId}")
    public ResponseEntity<?> getProduct(@PathVariable long id, @PathVariable long optionId){
        try {
            return new ResponseEntity<>(productService.getProductOption(id,optionId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@ModelAttribute CreateProductRequest createProductRequest, @RequestParam(required = false) MultipartFile[] multipartFiles, @PathVariable long id,@RequestParam(required = false) Long[] imageIdDelete) throws IOException {
        try {
            productService.updateProduct(createProductRequest, id, multipartFiles, imageIdDelete);
            return new ResponseEntity<>("update success!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Delete product success!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "", required = false) String title,@RequestParam( required = false) String brandName, @RequestParam(defaultValue = "", required = false) String categoryName,@RequestParam(defaultValue = "0", required = false) int minPrice,
            @RequestParam(defaultValue = "100000", required = false) int maxPrice,@RequestParam(required = false, defaultValue = "price_low") String sort, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "5") int size ) {
        try {
            return new ResponseEntity<>(productService.getAllProduct(title,brandName, categoryName,sort,minPrice,maxPrice,page,size ), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
