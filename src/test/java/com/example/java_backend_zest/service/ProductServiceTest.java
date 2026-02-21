package com.example.java_backend_zest.service;



import com.example.java_backend_zest.Dto.ProductRequest;
import com.example.java_backend_zest.Repository.ProductRepository;
import com.example.java_backend_zest.Service.ProductService;
import com.example.java_backend_zest.entity.Product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest request = new ProductRequest();
        request.setProductName("Test Product");

        Product saved = new Product();
        saved.setId(1L);
        saved.setProductName("Test Product");

        when(repository.save(any(Product.class))).thenReturn(saved);

        Product result = service.addProduct(request);
        assertEquals("Test Product", result.getProductName());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getProductById(1L));
        assertEquals("Product not found", ex.getMessage());
    }
}