package com.prueba.pokeapi.repository;

import com.prueba.pokeapi.model.Ability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
    Ability findByName(String name);
}
