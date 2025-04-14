package com.prueba.pokeapi.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Ability() {}
    public Ability(String name) {
        this.name = name;
    }
    // Getters y setters
}
