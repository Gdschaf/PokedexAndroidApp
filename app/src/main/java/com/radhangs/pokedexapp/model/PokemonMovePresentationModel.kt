package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.shared.convertToTitle

enum class DamageType {
    PHYSICAL,
    SPECIAL,
    STATUS,
    UNKNOWN
}

val DamageTypeMap = mapOf(
    "physical" to DamageCategoryPresentationModel(
        DamageType.PHYSICAL,
        R.string.physical_damage_category,
        R.drawable.physical_move_icon
    ),
    "special" to DamageCategoryPresentationModel(
        DamageType.SPECIAL,
        R.string.special_damage_category,
        R.drawable.special_move_icon
    ),
    "status" to DamageCategoryPresentationModel(
        DamageType.STATUS,
        R.string.status_damage_category,
        R.drawable.status_move_icon
    )
)

data class DamageCategoryPresentationModel(
    val type: DamageType,
    val stringResourceId: Int,
    val drawableResourceId: Int
) {
    companion object {
        val empty = DamageCategoryPresentationModel(DamageType.UNKNOWN, 0, 0)

        fun getCategory(damageCategory: String?) = DamageTypeMap[damageCategory] ?: empty
    }
}

enum class LearnType {
    LEVEL_UP,
    MACHINE,
    TUTOR,
    UNKNOWN
}

val LearnTypeMap = mapOf(
    "level-up" to LearnType.LEVEL_UP,
    "machine" to LearnType.MACHINE,
    "tutor" to LearnType.TUTOR
)

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
        damageType = DamageCategoryPresentationModel.getCategory(pokemon_v2_move?.pokemon_v2_movedamageclass?.name)
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