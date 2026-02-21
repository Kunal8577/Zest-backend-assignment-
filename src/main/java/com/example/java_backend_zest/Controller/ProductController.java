package com.example.java_backend_zest.Controller;

import com.example.java_backend_zest.Dto.ProductRequest;
import com.example.java_backend_zest.Service.ProductService;
import com.example.java_backend_zest.entity.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public Page<Product> getAll(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size) {
        return service.getAllProduct(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> create(
            @Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(service.addProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
