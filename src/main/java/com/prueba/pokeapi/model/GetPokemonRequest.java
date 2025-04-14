package com.prueba.pokeapi.model;

import jakarta.xml.bind.annotation.*;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name"
})
@XmlRootElement(name = "getPokemonRequest", namespace = "http://example.com/pokeapi/pokemons")
public class GetPokemonRequest {

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons", required = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }
}
