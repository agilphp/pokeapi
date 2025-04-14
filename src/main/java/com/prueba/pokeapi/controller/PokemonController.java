package com.prueba.pokeapi.controller;

import com.prueba.pokeapi.dtos.PokemonDTO;
import com.prueba.pokeapi.model.Pokemon;
import com.prueba.pokeapi.service.PokemonService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/sync")
    public String syncFromApi() {
        pokemonService.syncPokemonsFromAPI();
        return "Sincronización completada con éxito";
    }

    @GetMapping("/pokemons")
    public List<PokemonDTO> getAll() {
        return pokemonService.getAll();  // Cambia el tipo de retorno a PokemonDTO
    }

    @GetMapping("/pokemons/name/{name}")
    public Pokemon getByName(@PathVariable String name) {
        return pokemonService.findByName(name);
    }

    @GetMapping("/pokemons/type/{type}")
    public List<Pokemon> getByType(@PathVariable String type) {
        return pokemonService.findByType(type);
    }

    @GetMapping("/pokemons/ability/{ability}")
    public List<Pokemon> getByAbility(@PathVariable String ability) {
        return pokemonService.findByAbility(ability);
    }
}
