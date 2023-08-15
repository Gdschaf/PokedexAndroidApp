package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.shared.convertToTitle

fun PokemonMovesQuery.Pokemon_v2_movelearnmethod.getLearnType() =
    if (LearnTypeMap.containsKey(name)) LearnTypeMap[name] else LearnType.UNKNOWN

fun PokemonMovesQuery.Pokemon_v2_pokemonmofe.toPresentationModel() =
    PokemonMovePresentationModel(
        moveName = pokemon_v2_move?.name?.convertToTitle() ?: "",
        accuracy = pokemon_v2_move?.accuracy ?: 100,
        power = pokemon_v2_move?.power,
        pp = pokemon_v2_move?.pp ?: 0, // uuuuh no shot this should be null, right?
        type = pokemon_v2_move?.pokemon_v2_type?.let {
            PokemonTypeWithResources.getType(it.name)
        } ?: PokemonTypeWithResources.unknown,
        learnType = pokemon_v2_movelearnmethod?.getLearnType() ?: LearnType.UNKNOWN,
        learnLevel = if (level <= 0) null else level,
        damageType = DamageCategoryPresentationModel.getCategory(
            pokemon_v2_move?.pokemon_v2_movedamageclass?.name
        )
    )

data class PokemonMovePresentationModel(
    val moveName: String,
    val accuracy: Int,
    val power: Int?,
    val pp: Int,
    val type: PokemonTypeWithResources,
    val learnType: LearnType,
    val learnLevel: Int?,
    val damageType: DamageCategoryPresentationModel
)
