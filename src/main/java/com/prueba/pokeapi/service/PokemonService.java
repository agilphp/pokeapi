package com.prueba.pokeapi.service;
import com.prueba.pokeapi.dtos.PokemonDTO;
import com.prueba.pokeapi.model.Pokemon;
import java.util.List;

public interface PokemonService {
    void syncPokemonsFromAPI();
    List<PokemonDTO> getAll();
    List<Pokemon> findByType(String type);
    List<Pokemon> findByAbility(String ability);
    Pokemon findByName(String name);
}
