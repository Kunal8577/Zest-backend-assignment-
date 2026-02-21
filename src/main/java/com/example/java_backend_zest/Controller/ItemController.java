package com.example.java_backend_zest.Controller;


import com.example.java_backend_zest.Dto.ApiResponse;
import com.example.java_backend_zest.Service.ItemService;
import com.example.java_backend_zest.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/{productId}/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> getItemsByProduct(@PathVariable Long productId) {
        List<Item> items = itemService.getItemsByProductId(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, items, "Items fetched successfully"));
    }
}
