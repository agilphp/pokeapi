package com.prueba.pokeapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int height;
    private int weight;

    @Column(name = "base_experience")
    private int baseExperience;

    @ManyToMany(fetch = FetchType.EAGER)  // Se asegura de que los tipos se carguen de inmediato
    @JoinTable(
            name = "pokemon_type",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<Type> types;

    @ManyToMany(fetch = FetchType.EAGER)  // Se asegura de que las habilidades se carguen de inmediato
    @JoinTable(
            name = "pokemon_ability",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "ability_id")
    )
    private Set<Ability> abilities;
}
