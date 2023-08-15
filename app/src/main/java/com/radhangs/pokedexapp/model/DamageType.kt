package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.R

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