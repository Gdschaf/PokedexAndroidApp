package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.R

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

fun getDrawableTypeIcon(typeValue: PokemonType): Int =
    when (typeValue) {
        PokemonType.NORMAL -> R.drawable.pokemon_type_icon_normal
        PokemonType.FIGHTING -> R.drawable.pokemon_type_icon_fighting
        PokemonType.FLYING -> R.drawable.pokemon_type_icon_flying
        PokemonType.POISON -> R.drawable.pokemon_type_icon_poison
        PokemonType.GROUND -> R.drawable.pokemon_type_icon_ground
        PokemonType.ROCK -> R.drawable.pokemon_type_icon_rock
        PokemonType.BUG -> R.drawable.pokemon_type_icon_bug
        PokemonType.GHOST -> R.drawable.pokemon_type_icon_ghost
        PokemonType.STEEL -> R.drawable.pokemon_type_icon_steel
        PokemonType.FIRE -> R.drawable.pokemon_type_icon_fire
        PokemonType.WATER -> R.drawable.pokemon_type_icon_water
        PokemonType.GRASS -> R.drawable.pokemon_type_icon_grass
        PokemonType.ELECTRIC -> R.drawable.pokemon_type_icon_electric
        PokemonType.PSYCHIC -> R.drawable.pokemon_type_icon_psychic
        PokemonType.ICE -> R.drawable.pokemon_type_icon_ice
        PokemonType.DRAGON -> R.drawable.pokemon_type_icon_dragon
        PokemonType.DARK -> R.drawable.pokemon_type_icon_dark
        PokemonType.FAIRY -> R.drawable.pokemon_type_icon_fairy
        else -> R.drawable.pokemon_type_icon_normal
    }