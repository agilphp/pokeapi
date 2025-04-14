package com.prueba.pokeapi.service.impl;

import com.prueba.pokeapi.dtos.PokemonDTO;
import com.prueba.pokeapi.model.Ability;
import com.prueba.pokeapi.model.Pokemon;
import com.prueba.pokeapi.model.Type;
import com.prueba.pokeapi.repository.AbilityRepository;
import com.prueba.pokeapi.repository.PokemonRepository;
import com.prueba.pokeapi.repository.TypeRepository;
import com.prueba.pokeapi.service.PokemonService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;
    private final TypeRepository typeRepository;
    private final AbilityRepository abilityRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public PokemonServiceImpl(PokemonRepository pokemonRepository,
                              TypeRepository typeRepository,
                              AbilityRepository abilityRepository) {
        this.pokemonRepository = pokemonRepository;
        this.typeRepository = typeRepository;
        this.abilityRepository = abilityRepository;
    }

    @Override
    public List<Pokemon> findByType(String type) {
        Type foundType = typeRepository.findByName(type);
        if (foundType == null) {
            return Collections.emptyList();
        }

        return pokemonRepository.findAll().stream()
                .filter(p -> p.getTypes().contains(foundType))
                .toList();
    }

    @Override
    public List<Pokemon> findByAbility(String ability) {
        Ability foundAbility = abilityRepository.findByName(ability);
        if (foundAbility == null) {
            return Collections.emptyList();
        }

        return pokemonRepository.findAll().stream()
                .filter(p -> p.getAbilities().contains(foundAbility))
                .toList();
    }

    @Override
    public void syncPokemonsFromAPI() {
        String baseUrl = "https://pokeapi.co/api/v2/pokemon";
        int limit = 100; // puedes ajustar el tamaño de página
        int offset = 0;

        Map<String, Type> typeCache = new HashMap<>();
        Map<String, Ability> abilityCache = new HashMap<>();

        // Obtener total de pokémon (count)
        String initialUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("limit", 1)
                .queryParam("offset", 0)
                .toUriString();

        Map<?, ?> initialResponse = restTemplate.getForObject(initialUrl, Map.class);
        int total = (int) initialResponse.get("count");

        // Paginación
        while (offset < total) {
            String pageUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .toUriString();

            Map<?, ?> response = restTemplate.getForObject(pageUrl, Map.class);
            List<Map<String, String>> results = (List<Map<String, String>>) response.get("results");

            for (Map<String, String> item : results) {
                try {
                    Map<?, ?> fullData = restTemplate.getForObject(item.get("url"), Map.class);
                    String name = (String) fullData.get("name");
                    int height = (int) fullData.get("height");
                    int weight = (int) fullData.get("weight");
                    int baseExp = (int) fullData.get("base_experience");

                    Set<Type> types = extractTypes(fullData, typeCache);
                    Set<Ability> abilities = extractAbilities(fullData, abilityCache);

                    if (pokemonRepository.findByName(name) == null) {
                        Pokemon p = new Pokemon();
                        p.setName(name);
                        p.setHeight(height);
                        p.setWeight(weight);
                        p.setBaseExperience(baseExp);
                        p.setTypes(types);
                        p.setAbilities(abilities);
                        pokemonRepository.save(p);
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar el Pokémon: " + item.get("name") + " - " + e.getMessage());
                }
            }

            offset += limit; // siguiente página
        }
    }


    private Set<Type> extractTypes(Map<?, ?> fullData, Map<String, Type> typeCache) {
        Set<Type> types = new HashSet<>();
        List<Map<String, Object>> typesList = (List<Map<String, Object>>) fullData.get("types");
        for (Map<String, Object> typeEntry : typesList) {
            Map<String, String> typeInfo = (Map<String, String>) typeEntry.get("type");
            String typeName = typeInfo.get("name");

            Type type = typeCache.computeIfAbsent(typeName, t -> {
                Type existing = typeRepository.findByName(t);
                return existing != null ? existing : typeRepository.save(new Type(t));
            });

            types.add(type);
        }
        return types;
    }

    private Set<Ability> extractAbilities(Map<?, ?> fullData, Map<String, Ability> abilityCache) {
        Set<Ability> abilities = new HashSet<>();
        List<Map<String, Object>> abilitiesList = (List<Map<String, Object>>) fullData.get("abilities");
        for (Map<String, Object> abEntry : abilitiesList) {
            Map<String, String> abInfo = (Map<String, String>) abEntry.get("ability");
            String abName = abInfo.get("name");

            Ability ability = abilityCache.computeIfAbsent(abName, a -> {
                Ability existing = abilityRepository.findByName(a);
                return existing != null ? existing : abilityRepository.save(new Ability(a));
            });

            abilities.add(ability);
        }
        return abilities;
    }
    @Override
    public List<PokemonDTO> getAll() {
        return pokemonRepository.findAll().stream().map(p -> {
            PokemonDTO dto = new PokemonDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setTypes(p.getTypes().stream().map(Type::getName).toList());
            return dto;
        }).toList();
    }

    @Override
    public Pokemon findByName(String name) {
        return pokemonRepository.findByName(name);
    }

    // Método para mapear de Pokemon a PokemonDTO
    private PokemonDTO mapToDTO(Pokemon pokemon) {
        PokemonDTO dto = new PokemonDTO();
        dto.setId(pokemon.getId());
        dto.setName(pokemon.getName());
        dto.setTypes(pokemon.getTypes().stream().map(Type::getName).toList());
        return dto;
    }
}
