package com.prueba.pokeapi.repository;

import com.prueba.pokeapi.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
