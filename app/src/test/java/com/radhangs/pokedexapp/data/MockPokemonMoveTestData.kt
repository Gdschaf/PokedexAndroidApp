package com.radhangs.pokedexapp.data

import com.radhangs.pokedexapp.PokemonMovesQuery
import com.radhangs.pokedexapp.model.DamageType
import com.radhangs.pokedexapp.model.LearnType
import com.radhangs.pokedexapp.model.PokemonMovePresentationData
import com.radhangs.pokedexapp.model.PokemonType

val mockLearnLevelUpNetworkData = PokemonMovesQuery.Pokemon_v2_movelearnmethod(
    name = "level-up"
)

val mockLearnMachineNetworkData = PokemonMovesQuery.Pokemon_v2_movelearnmethod(
    name = "machine"
)

val mockDamageClassStatusNetworkData = PokemonMovesQuery.Pokemon_v2_movedamageclass(
    name = "status"
)

val mockDamageClassPhysicalNetworkData = PokemonMovesQuery.Pokemon_v2_movedamageclass(
    name = "physical"
)

val mockSwordsDanceNetworkData = PokemonMovesQuery.Pokemon_v2_move(
    name = "swords-dance",
    accuracy = 100,
    pp = 10,
    power = null,
    pokemon_v2_type = PokemonMovesQuery.Pokemon_v2_type(
        name = "normal"
    ),
    pokemon_v2_movedamageclass = mockDamageClassStatusNetworkData
)

val mockRazorLeafNetworkData = PokemonMovesQuery.Pokemon_v2_move(
    name = "razor-leaf",
    accuracy = 95,
    pp = 25,
    power = 55,
    pokemon_v2_type = PokemonMovesQuery.Pokemon_v2_type(
        name = "grass"
    ),
    pokemon_v2_movedamageclass = mockDamageClassPhysicalNetworkData
)

val mockNullMoveNetworkData = PokemonMovesQuery.Pokemon_v2_move(
    name = "growl",
    accuracy = null,
    pp = null,
    power = null,
    pokemon_v2_type = null,
    pokemon_v2_movedamageclass = null
)

val mockPokemonMovesNullNetworkData = PokemonMovesQuery.Pokemon_v2_pokemon_by_pk(
    listOf(
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = null,
            pokemon_v2_movelearnmethod = null,
            level = 0
        )
    )
)

val mockPokemonMovesNetworkData = PokemonMovesQuery.Pokemon_v2_pokemon_by_pk(
    listOf(
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = mockSwordsDanceNetworkData,
            pokemon_v2_movelearnmethod = mockLearnMachineNetworkData,
            level = 0
        ),
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = mockNullMoveNetworkData,
            pokemon_v2_movelearnmethod = null,
            level = 0
        ),
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = mockRazorLeafNetworkData,
            pokemon_v2_movelearnmethod = mockLearnLevelUpNetworkData,
            level = 27
        )
    )
)

val mockPokemonMovesDuplicateNetworkData = PokemonMovesQuery.Pokemon_v2_pokemon_by_pk(
    listOf(
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = mockSwordsDanceNetworkData,
            pokemon_v2_movelearnmethod = mockLearnMachineNetworkData,
            level = 0
        ),
        PokemonMovesQuery.Pokemon_v2_pokemonmofe(
            pokemon_v2_move = mockSwordsDanceNetworkData,
            pokemon_v2_movelearnmethod = mockLearnMachineNetworkData,
            level = 0
        )
    )
)

val mockPokemonMovesPresentationData = listOf(
    PokemonMovePresentationData(
        moveName = "Razor Leaf",
        accuracy = 95,
        power = 55,
        pp = 25,
        type = PokemonType.GRASS,
        learnType = LearnType.LEVEL_UP,
        learnLevel = 27,
        damageType = DamageType.PHYSICAL
    ),
    PokemonMovePresentationData(
        moveName = "Swords Dance",
        accuracy = 100,
        power = null,
        pp = 10,
        type = PokemonType.NORMAL,
        learnType = LearnType.MACHINE,
        learnLevel = null,
        damageType = DamageType.STATUS
    ),
    PokemonMovePresentationData(
        moveName = "Growl",
        accuracy = 100,
        power = null,
        pp = 0,
        type = PokemonType.UNKNOWN,
        learnType = LearnType.UNKNOWN,
        learnLevel = null,
        damageType = DamageType.UNKNOWN
    )
)

val mockPokemonSwordsDancePresentationData = listOf(
    PokemonMovePresentationData(
        moveName = "Swords Dance",
        accuracy = 100,
        power = null,
        pp = 10,
        type = PokemonType.NORMAL,
        learnType = LearnType.MACHINE,
        learnLevel = null,
        damageType = DamageType.STATUS
    )
)