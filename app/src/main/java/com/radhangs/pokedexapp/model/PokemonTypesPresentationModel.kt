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

data class PokemonTypesPresentationModel(
    val mainType: PokemonType,
    val secondaryType: PokemonType? = null
) {
    companion object
    {
        val empty = PokemonTypesPresentationModel(PokemonType.NORMAL)

        // todo, this isn't the best pattern, maybe figure out something else?
        fun fromDetailsNetworkData(
            listOfTypes: List<PokemonDetailQuery.Pokemon_v2_pokemontype>
        ) = fromPokedexNetworkData(
            listOfTypes.map {
                PokedexQuery.Pokemon_v2_pokemontype(PokedexQuery.Pokemon_v2_type(name = it.pokemon_v2_type?.name ?: ""))
            }
        )

        fun fromPokedexNetworkData(
            listOfTypes: List<PokedexQuery.Pokemon_v2_pokemontype>?
        ): PokemonTypesPresentationModel {
            if(listOfTypes.isNullOrEmpty())
                return empty

            // val mappedItems = listOfTypes.take(2).map {type ->
            //     type.type_id?.let { id ->
            //         typeRepository.getPokemonTypeById(id)
            //     } ?: ""
            // }

            // we should be able to do this a bit more elegantly honestly...
            var mainType: PokemonType? = null
            var secondType: PokemonType? = null
            for(i in listOfTypes.indices)
            {
                listOfTypes[i].pokemon_v2_type?.let {type ->
                    val typeString = type.name
                    if(typeString.isNotEmpty())
                    {
                        when(i)
                        {
                            0 -> mainType = PokemonTypeMap[typeString]
                            1 -> secondType = PokemonTypeMap[typeString]
                            // else -> // UUUH WHAT POKEMON HAS MORE THEN 2 TYPES? something is wrong... log something
                        }
                    }
                }
            }

            return mainType?.let {
                PokemonTypesPresentationModel(it, secondType)
            } ?: empty
        }
    }
}