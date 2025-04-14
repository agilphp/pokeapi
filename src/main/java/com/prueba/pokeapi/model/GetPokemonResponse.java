package com.prueba.pokeapi.model;
import jakarta.xml.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "height",
        "weight",
        "baseExperience",
        "types",
        "abilities"
})
@XmlRootElement(name = "getPokemonResponse", namespace = "http://example.com/pokeapi/pokemons")
public class GetPokemonResponse {

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons", required = true)
    private String name;

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons")
    private int height;

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons")
    private int weight;

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons")
    private int baseExperience;

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons", required = true)
    private List<String> types;

    @XmlElement(namespace = "http://example.com/pokeapi/pokemons", required = true)
    private List<String> abilities;

    public GetPokemonResponse() {
        this.types = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    // Getters y setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getBaseExperience() { return baseExperience; }
    public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }

    public List<String> getTypes() { return types; }
    public List<String> getAbilities() { return abilities; }
}
