package com.example.java_backend_zest.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item")
@Setter
@Getter

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Integer quantity;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

}
