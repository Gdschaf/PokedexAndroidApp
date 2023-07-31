package com.radhangs.pokedexapp.model

// Pulling this data from the API but converting ids/names to enum types for ease of use
// At this time, these are all the types being reported by the API
enum class PokemonType {
    NORMAL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    UNKNOWN,
    SHADOW
}

// TODO create tests for this...
val PokemonTypeMap = mapOf(
    "normal" to PokemonType.NORMAL,
    "fighting" to PokemonType.FIGHTING,
    "flying" to PokemonType.FLYING,
    "poison" to PokemonType.POISON,
    "ground" to PokemonType.GROUND,
    "rock" to PokemonType.ROCK,
    "bug" to PokemonType.BUG,
    "ghost" to PokemonType.GHOST,
    "steel" to PokemonType.STEEL,
    "fire" to PokemonType.FIRE,
    "water" to PokemonType.WATER,
    "grass" to PokemonType.GRASS,
    "electric" to PokemonType.ELECTRIC,
    "psychic" to PokemonType.PSYCHIC,
    "ice" to PokemonType.ICE,
    "dragon" to PokemonType.DRAGON,
    "dark" to PokemonType.DARK,
    "fairy" to PokemonType.FAIRY,
    "unknown" to PokemonType.UNKNOWN,
    "shadow" to PokemonType.SHADOW
)