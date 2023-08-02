package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonDetailQuery
import com.radhangs.pokedexapp.shared.capitalizeFirstLetter

// this is named too similarly to the PokemonTypes enum...
data class PokemonPresentationTypes(
    val mainType: PokemonType,
    val secondaryType: PokemonType? = null
) {
    companion object
    {
        val empty = PokemonPresentationTypes(PokemonType.NORMAL)

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
                PokemonPresentationTypes(it, secondType)
            } ?: empty
        }
    }
}

// not terrible, could add some helper functions to help with ui
// this is essentially what will be used by the lazy list for the main page
data class PokedexPresentationModel(
    val pokemonId: Int,
    val pokemonName: String,
    val pokemonTypes: PokemonPresentationTypes,
    val spriteUri: String?
) {
    companion object
    {
        fun fromNetworkData(
            pokemon: PokedexQuery.Pokemon_v2_pokemon
        ) : PokedexPresentationModel =
            PokedexPresentationModel(
                pokemonId = pokemon.id,
                pokemonName = pokemon.name.capitalizeFirstLetter(),
                pokemonTypes = PokemonPresentationTypes.fromPokedexNetworkData(
                    pokemon.pokemon_v2_pokemontypes
                ),
                spriteUri = getFrontDefaultSprite(pokemon.pokemon_v2_pokemonsprites.first().sprites)
            )
    }
}

// TODO the below functions could either be put into their respective classes they help or a larger helper class that's more global?

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