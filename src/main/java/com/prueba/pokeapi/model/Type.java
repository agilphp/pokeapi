package com.prueba.pokeapi.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Type() {}
    public Type(String name) {
        this.name = name;
    }
    // Getters y setters
}
