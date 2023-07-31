package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery.Pokemon_v2_pokemonsprite
import com.radhangs.pokedexapp.repository.PokemonTypeRepository

// this is named too similarly to the PokemonTypes enum...
data class PokemonPresentationTypes(val mainType: PokemonType, val secondaryType: PokemonType? = null)
{
    companion object
    {
        val empty = PokemonPresentationTypes(PokemonType.NORMAL)
    }
}

// not terrible, could add some helper functions to help with ui, this is essentially what will be used by the lazy list for the main page
data class PokedexPresentationModel(
    val pokemonName: String,
    val pokemonPresentationTypes: PokemonPresentationTypes
)

// maybe just do 1 converter instead of for the list cuz we can use map for the list?
// fun PokedexNetworkToUiAdapter(networkPokemon: Pokemon_v2_pokemonsprite) : PokedexModel =

// rename this, it's silly
// wouldn't it be nice if the type repository was just injectable?!
fun ConvertToUiModel(pokemonSpriteData: List<Pokemon_v2_pokemonsprite>, typeRepository: PokemonTypeRepository): List<PokedexPresentationModel> =
    pokemonSpriteData.map { sprite ->
        PokedexPresentationModel(
            pokemonName = sprite.pokemon_v2_pokemon?.name ?: "",
            pokemonPresentationTypes = PokemonPresentationTypes(PokemonType.NORMAL)
        )
    }

// fun ToPokemonTypes(listOfTypes: List<String>): PokemonType
// {
//     val mappedItems = listOfTypes.take(2).map { (main, secondary) ->  }
// }