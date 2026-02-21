package com.example.java_backend_zest.Service;

import com.example.java_backend_zest.Dto.ProductRequest;
import com.example.java_backend_zest.Repository.ProductRepository;
import com.example.java_backend_zest.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // ✅ CORRECT
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getAllProduct(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id){
        Product product=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product is Not Found"));
        return product;
    }

    public Product addProduct(ProductRequest request){
        Product product1=new Product();
        product1.setProductName(request.getProductName());
        product1.setCreatedBy("SYSTEM");
        product1.setCreatedOn(LocalDateTime.now());
        return productRepository.save(product1);
    }
    public Product updateProduct(Long id,ProductRequest productRequest)
    {
        Product product = getProductById(id);
        product.setProductName(productRequest.getProductName());
        product.setModifiedBy("SYSTEM");
        product.setModifiedOn(LocalDateTime.now());
        return productRepository.save(product);
    }

    public void delete(Long id){
         productRepository.deleteById(id);
    }

}
