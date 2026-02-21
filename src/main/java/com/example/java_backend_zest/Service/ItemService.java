package com.example.java_backend_zest.Service;

import com.example.java_backend_zest.Repository.ItemRepository;
import com.example.java_backend_zest.Repository.ProductRepository;
import com.example.java_backend_zest.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepo;
    private final ProductRepository productRepo;

    public List<Item> getItemsByProductId(Long productId) {
        if (!productRepo.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        return itemRepo.findByProductId(productId);
    }
}
