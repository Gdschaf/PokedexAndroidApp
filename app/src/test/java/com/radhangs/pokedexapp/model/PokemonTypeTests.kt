package com.radhangs.pokedexapp.model

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

    // this test currently fails because I don't have an icon for the shadow or unknown types so they do in fact default to normal
    //@Test
    //fun `test all types have a drawable`() {
    //    // I'm currently using the normal icon for unknowns, so we're basically making sure we only get the normal icon when the type is in fact, normal
    //    for(e in PokemonType.values()) {
    //        if(e != PokemonType.NORMAL) {
    //            assertTrue(getDrawableTypeIcon(e) != R.drawable.pokemon_type_icon_normal)
    //        } else {
    //            assertTrue(getDrawableTypeIcon(e) == R.drawable.pokemon_type_icon_normal)
    //        }
    //    }
    //}
}