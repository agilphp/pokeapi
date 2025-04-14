package com.prueba.pokeapi.endpoint;

import com.prueba.pokeapi.model.GetPokemonRequest;
import com.prueba.pokeapi.model.GetPokemonResponse;
import com.prueba.pokeapi.model.Pokemon;
import com.prueba.pokeapi.service.PokemonService;
import org.springframework.ws.server.endpoint.annotation.*;

@Endpoint
public class PokemonEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/pokeapi/pokemons";

    private final PokemonService pokemonService;

    public PokemonEndpoint(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonRequest")
    @ResponsePayload
    public GetPokemonResponse getPokemon(@RequestPayload GetPokemonRequest request) {
        GetPokemonResponse response = new GetPokemonResponse();

        // Obtener el Pokémon de la base de datos por su nombre
        Pokemon pokemon = pokemonService.findByName(request.getName());
        if (pokemon != null) {
            // Asignar los atributos del Pokémon a la respuesta
            response.setName(pokemon.getName());
            response.setHeight(pokemon.getHeight());
            response.setWeight(pokemon.getWeight());
            response.setBaseExperience(pokemon.getBaseExperience());

            // Convertir los tipos de Pokémon (Set<Type>) a una lista de Strings
            response.getTypes().addAll(pokemon.getTypes().stream()
                    .map(type -> type.getName()) // Obtener el nombre del tipo
                    .toList());

            // Convertir las habilidades de Pokémon (Set<Ability>) a una lista de Strings
            response.getAbilities().addAll(pokemon.getAbilities().stream()
                    .map(ability -> ability.getName()) // Obtener el nombre de la habilidad
                    .toList());
        }

        return response;
    }
}
