package com.prueba.pokeapi.endpoint;

import com.prueba.pokeapi.model.GetPokemonRequest;
import com.prueba.pokeapi.model.GetPokemonResponse;
import org.springframework.ws.server.endpoint.annotation.*;

@Endpoint
public class PokemonEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/pokeapi/pokemons";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonRequest")
    @ResponsePayload
    public GetPokemonResponse getPokemon(@RequestPayload GetPokemonRequest request) {
        GetPokemonResponse response = new GetPokemonResponse();
        response.setName(request.getName());
        response.setHeight(4);
        response.setWeight(60);
        response.setBaseExperience(112);
        response.getTypes().add("electric");
        response.getAbilities().add("static");
        response.getAbilities().add("lightning-rod");
        return response;
    }
}
