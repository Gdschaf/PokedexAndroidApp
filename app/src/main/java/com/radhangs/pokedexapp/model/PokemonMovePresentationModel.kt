package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.shared.ConvertToTitle

enum class DamageType {
    PHYSICAL,
    SPECIAL,
    STATUS,
    UNKNOWN
}

val DamageTypeMap = mapOf(
    "physical" to DamageType.PHYSICAL,
    "special" to DamageType.SPECIAL,
    "status" to DamageType.STATUS
)

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

data class PokemonMovePresentationModel(
    val moveName: String,
    val accuracy: Int,
    val power: Int?,
    val pp: Int,
    val type: PokemonTypeWithResources,
    val learnType: LearnType,
    val learnLevel: Int?,
    val damageType: DamageType
) {
    // this is a tad better, but it ain't great
    companion object {
        fun fromNetworkData(moveInfo: PokemonMovesQuery.Pokemon_v2_pokemonmofe) =
            PokemonMovePresentationModel(
                moveName = moveInfo.pokemon_v2_move?.name?.ConvertToTitle() ?: "",
                accuracy = moveInfo.pokemon_v2_move?.accuracy ?: 100,
                power = moveInfo.pokemon_v2_move?.power,
                pp = moveInfo.pokemon_v2_move?.pp ?: 0, // uuuuh no shot this should be null, right?
                type = moveInfo.pokemon_v2_move?.pokemon_v2_type?.let {
                    PokemonTypeWithResources.getType(it.name)
                } ?: PokemonTypeWithResources.unknown,
                learnType = getLearnType(moveInfo.pokemon_v2_movelearnmethod),
                learnLevel = if (moveInfo.level <= 0) null else moveInfo.level,
                damageType = getDamageType(moveInfo.pokemon_v2_move)
            )

        fun getDrawableDamageTypeIcon(type: DamageType): Int =
            when (type) {
                DamageType.PHYSICAL -> R.drawable.physical_move_icon
                DamageType.SPECIAL -> R.drawable.special_move_icon
                DamageType.STATUS -> R.drawable.status_move_icon
                else -> R.drawable.physical_move_icon // todo change this out to something better
            }

        fun getDamageType(move: PokemonMovesQuery.Pokemon_v2_move?) =
            move?.pokemon_v2_movedamageclass?.let { damageClass ->
                DamageTypeMap[damageClass.name]
            } ?: DamageType.UNKNOWN


        fun getLearnType(learnMethod: PokemonMovesQuery.Pokemon_v2_movelearnmethod?) =
            learnMethod?.let {
                LearnTypeMap[it.name]
            } ?: LearnType.UNKNOWN
    }
}