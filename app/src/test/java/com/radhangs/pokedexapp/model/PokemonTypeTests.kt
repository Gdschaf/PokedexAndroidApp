package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.PokedexQuery
import com.radhangs.pokedexapp.PokemonDetailQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class PokemonTypeTests {

    @Test
    fun `test parsing to type map`() {
        assertEquals(PokemonType.NORMAL, PokemonTypeWithResources.getType("normal").type)
        assertEquals(PokemonType.FIGHTING, PokemonTypeWithResources.getType("fighting").type)
        assertEquals(PokemonType.FLYING, PokemonTypeWithResources.getType("flying").type)
        assertEquals(PokemonType.POISON, PokemonTypeWithResources.getType("poison").type)
        assertEquals(PokemonType.GROUND, PokemonTypeWithResources.getType("ground").type)
        assertEquals(PokemonType.ROCK, PokemonTypeWithResources.getType("rock").type)
        assertEquals(PokemonType.BUG, PokemonTypeWithResources.getType("bug").type)
        assertEquals(PokemonType.GHOST, PokemonTypeWithResources.getType("ghost").type)
        assertEquals(PokemonType.STEEL, PokemonTypeWithResources.getType("steel").type)
        assertEquals(PokemonType.FIRE, PokemonTypeWithResources.getType("fire").type)
        assertEquals(PokemonType.WATER, PokemonTypeWithResources.getType("water").type)
        assertEquals(PokemonType.GRASS, PokemonTypeWithResources.getType("grass").type)
        assertEquals(PokemonType.ELECTRIC, PokemonTypeWithResources.getType("electric").type)
        assertEquals(PokemonType.PSYCHIC, PokemonTypeWithResources.getType("psychic").type)
        assertEquals(PokemonType.ICE, PokemonTypeWithResources.getType("ice").type)
        assertEquals(PokemonType.DRAGON, PokemonTypeWithResources.getType("dragon").type)
        assertEquals(PokemonType.DARK, PokemonTypeWithResources.getType("dark").type)
        assertEquals(PokemonType.FAIRY, PokemonTypeWithResources.getType("fairy").type)
        assertEquals(PokemonType.UNKNOWN, PokemonTypeWithResources.getType("unknown").type)
        assertEquals(PokemonType.SHADOW, PokemonTypeWithResources.getType("shadow").type)
    }

    @Test
    fun `test all types are in the type map`() {
        for(e in PokemonType.values()) {
            assertTrue(PokemonTypeMap.values.any { it.type == e })
        }
    }

    @Test
    fun `empty pokedex network data should adapt to unknown and null types`() {
        val list: List<PokedexQuery.Pokemon_v2_pokemontype> = emptyList()
        val test = list.pokedexToPresentationModel()
        assertEquals(PokemonTypeWithResources.unknown, test.mainType)
        assertEquals(null, test.secondaryType)
    }

    @Test
    fun `empty pokemon details network data should adapt to unknown and null types`() {
        val list: List<PokemonDetailQuery.Pokemon_v2_pokemontype> = emptyList()
        val test = list.detailToPresentationModel()
        assertEquals(PokemonTypeWithResources.unknown, test.mainType)
        assertEquals(null, test.secondaryType)
    }
}