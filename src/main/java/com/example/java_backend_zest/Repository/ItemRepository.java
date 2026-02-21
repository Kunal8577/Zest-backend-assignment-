package com.example.java_backend_zest.Repository;

import com.example.java_backend_zest.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByProductId(Long productId);

}
