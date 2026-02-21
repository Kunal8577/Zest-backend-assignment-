package com.example.java_backend_zest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product",indexes = @Index(name="idx_product_name",columnList = "product_name"))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_name",nullable=false)
    private String productName;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private String modifiedBy;

    private LocalDateTime modifiedOn;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<Item> item=new ArrayList<>();

}
