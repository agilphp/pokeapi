package com.prueba.pokeapi.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PokemonDTO {
    private Long id;
    private String name;
    private List<String> types;
}
