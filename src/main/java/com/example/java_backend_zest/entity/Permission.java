package com.example.java_backend_zest.entity;
// Permission.java
//package com.example.java_backend_zest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // e.g., READ_USER, WRITE_USER
}
