package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokedexQuery.Pokemon_v2_pokemontype
import com.radhangs.pokedexapp.repository.PokemonTypeRepository

// this is named too similarly to the PokemonTypes enum...
data class PokemonPresentationTypes(
    val mainType: PokemonType,
    val secondaryType: PokemonType? = null
) {
    companion object
    {
        val empty = PokemonPresentationTypes(PokemonType.NORMAL)

        fun fromNetworkData(
            listOfTypes: List<Pokemon_v2_pokemontype>?,
            typeRepository: PokemonTypeRepository
        ): PokemonPresentationTypes {
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
                listOfTypes[i].type_id?.let {id ->
                    val typeString = typeRepository.getPokemonTypeById(id)
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
                PokemonPresentationTypes(it, secondType)
            } ?: empty
        }
    }
}

// not terrible, could add some helper functions to help with ui
// this is essentially what will be used by the lazy list for the main page
data class PokedexPresentationModel(
    val pokemonName: String,
    val pokemonPresentationTypes: PokemonPresentationTypes,
    val spriteUri: String?
) {
    companion object
    {
        fun fromNetworkData(
            pokemon: PokedexQuery.Pokemon_v2_pokemon,
            typeRepository: PokemonTypeRepository
        ) : PokedexPresentationModel =
            PokedexPresentationModel(
                pokemonName = capitalizeFirstLetter(pokemon.name),
                pokemonPresentationTypes = PokemonPresentationTypes.fromNetworkData(
                    pokemon.pokemon_v2_pokemontypes,
                    typeRepository
                ),
                spriteUri = getFrontDefaultSprite(pokemon.pokemon_v2_pokemonsprites.first().sprites)
            )
    }
}

fun capitalizeFirstLetter(string: String?) : String =
    string?.let { s ->
        s.replaceFirstChar { char -> char.uppercase() }
    } ?: ""

// create a test for if there's a map with no front_default key and see what it returns, make sure it doesn't break
fun getFrontDefaultSprite(input: String) = parseStringToMap(input)["front_default"] //front_default, front_transparent isn't for all pokemon
// TODO default to front_transparent, if it doesn't exist, do front_default, else just do the 0.png for a question mark sprite image

// could easily make a bunch of tests for this
fun parseStringToMap(input: String): Map<String, String> {
    val regex = """\s*"([^"]+)": "([^"]+)"""".toRegex()
    val matches = regex.findAll(input)

    val map = mutableMapOf<String, String>()
    for (match in matches) {
        val (key, value) = match.destructured
        map[key] = value
    }

    return map
}