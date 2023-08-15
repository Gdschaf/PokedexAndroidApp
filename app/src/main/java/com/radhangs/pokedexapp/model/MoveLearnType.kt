package com.radhangs.pokedexapp.model

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