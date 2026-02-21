package com.example.java_backend_zest.Repository;

import com.example.java_backend_zest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
