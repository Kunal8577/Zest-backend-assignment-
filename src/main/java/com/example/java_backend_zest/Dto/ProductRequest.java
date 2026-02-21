package com.example.java_backend_zest.Dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {

    @NotBlank(message="Product name is required")
    private String ProductName;
    @Column(nullable = false)
    private String createdBy;



}
