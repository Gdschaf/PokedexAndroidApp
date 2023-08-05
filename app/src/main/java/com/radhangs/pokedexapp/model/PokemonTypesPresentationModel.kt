package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonDetailQuery
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

val PokemonTypeMap = mapOf(
    "normal" to PokemonTypeWithResources(
        PokemonType.NORMAL,
        R.drawable.pokemon_type_icon_normal,
        R.string.normal_type_name
    ),
    "fighting" to PokemonTypeWithResources(
        PokemonType.FIGHTING,
        R.drawable.pokemon_type_icon_fighting,
        R.string.normal_type_name
    ),
    "flying" to PokemonTypeWithResources(
        PokemonType.FLYING,
        R.drawable.pokemon_type_icon_flying,
        R.string.flying_type_name
    ),
    "poison" to PokemonTypeWithResources(
        PokemonType.POISON,
        R.drawable.pokemon_type_icon_poison,
        R.string.poison_type_name
    ),
    "ground" to PokemonTypeWithResources(
        PokemonType.GROUND,
        R.drawable.pokemon_type_icon_ground,
        R.string.ground_type_name
    ),
    "rock" to PokemonTypeWithResources(
        PokemonType.ROCK,
        R.drawable.pokemon_type_icon_rock,
        R.string.rock_type_name
    ),
    "bug" to PokemonTypeWithResources(
        PokemonType.BUG,
        R.drawable.pokemon_type_icon_bug,
        R.string.bug_type_name
    ),
    "ghost" to PokemonTypeWithResources(
        PokemonType.GHOST,
        R.drawable.pokemon_type_icon_ghost,
        R.string.ghost_type_name
    ),
    "steel" to PokemonTypeWithResources(
        PokemonType.STEEL,
        R.drawable.pokemon_type_icon_steel,
        R.string.steel_type_name
    ),
    "fire" to PokemonTypeWithResources(
        PokemonType.FIRE,
        R.drawable.pokemon_type_icon_fire,
        R.string.fire_type_name
    ),
    "water" to PokemonTypeWithResources(
        PokemonType.WATER,
        R.drawable.pokemon_type_icon_water,
        R.string.water_type_name
    ),
    "grass" to PokemonTypeWithResources(
        PokemonType.GRASS,
        R.drawable.pokemon_type_icon_grass,
        R.string.grass_type_name
    ),
    "electric" to PokemonTypeWithResources(
        PokemonType.ELECTRIC,
        R.drawable.pokemon_type_icon_electric,
        R.string.electric_type_name
    ),
    "psychic" to PokemonTypeWithResources(
        PokemonType.PSYCHIC,
        R.drawable.pokemon_type_icon_psychic,
        R.string.psychic_type_name
    ),
    "ice" to PokemonTypeWithResources(
        PokemonType.ICE,
        R.drawable.pokemon_type_icon_ice,
        R.string.ice_type_name
    ),
    "dragon" to PokemonTypeWithResources(
        PokemonType.DRAGON,
        R.drawable.pokemon_type_icon_dragon,
        R.string.dragon_type_name
    ),
    "dark" to PokemonTypeWithResources(
        PokemonType.DARK,
        R.drawable.pokemon_type_icon_dark,
        R.string.dark_type_name
    ),
    "fairy" to PokemonTypeWithResources(
        PokemonType.FAIRY,
        R.drawable.pokemon_type_icon_fairy,
        R.string.fairy_type_name
    ),
    "unknown" to PokemonTypeWithResources.unknown,
    "shadow" to PokemonTypeWithResources(
        PokemonType.SHADOW,
        R.drawable.pokemon_type_icon_normal,
        R.string.shadow_name
    )
)

data class PokemonTypeWithResources(val type: PokemonType, val typeIconResourceId: Int, val typeStringResourceId: Int) {

    companion object {
        val unknown = PokemonTypeWithResources(
            PokemonType.UNKNOWN,
            R.drawable.pokemon_type_icon_normal,
            R.string.unknown_name
        )

        fun getType(typeName: String) = PokemonTypeMap[typeName] ?: unknown
    }
}

data class PokemonTypesPresentationModel(
    val mainType: PokemonTypeWithResources,
    val secondaryType: PokemonTypeWithResources? = null
) {
    companion object
    {
        val empty = PokemonTypesPresentationModel(PokemonTypeWithResources.unknown)

        fun fromDetailsNetworkData(
            listOfTypes: List<PokemonDetailQuery.Pokemon_v2_pokemontype>
        ) = PokemonTypesPresentationModel(
            mainType = listOfTypes.getOrNull(0)?.pokemon_v2_type?.let { type ->
                PokemonTypeWithResources.getType(type.name)
            } ?: PokemonTypeWithResources.unknown,
            secondaryType = listOfTypes.getOrNull(1)?.pokemon_v2_type?.let { type ->
                PokemonTypeWithResources.getType(type.name)
            }
        )

        fun fromPokedexNetworkData(
            listOfTypes: List<PokedexQuery.Pokemon_v2_pokemontype>?
        ) = PokemonTypesPresentationModel(
            mainType = listOfTypes?.getOrNull(0)?.pokemon_v2_type?.let { type ->
                PokemonTypeWithResources.getType(type.name)
            } ?: PokemonTypeWithResources.unknown,
            secondaryType = listOfTypes?.getOrNull(1)?.pokemon_v2_type?.let { type ->
                PokemonTypeWithResources.getType(type.name)
            }
        )
    }
}