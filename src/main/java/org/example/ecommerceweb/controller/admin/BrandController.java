package org.example.ecommerceweb.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/brands")
@RequiredArgsConstructor
@RestController
public class BrandController {
    private final BrandService brandService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name) {
        try {
            brandService.createBrand(name);
            return new ResponseEntity<>("Create brand success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestParam String name, @PathVariable("id") Long id) {
        try {
            brandService.updateBrand(name, id);
            return new ResponseEntity<>("Update brand success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            brandService.deleteBrand(id);
            return new ResponseEntity<>("Delete brand success!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(brandService.getBrand(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> list(@RequestParam(defaultValue = "", required = false) String name, @RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "5") int size) {
        try {
            return new ResponseEntity<>(brandService.getAllBrandsByName(name, page, size), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
