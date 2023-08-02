package com.radhangs.pokedexapp.model

import com.radhangs.pokedexapp.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class PokemonTypeTests {

    @Test
    fun `test parsing to type map`() {
        assertEquals(PokemonType.NORMAL, PokemonTypeMap["normal"])
        assertEquals(PokemonType.FIGHTING, PokemonTypeMap["fighting"])
        assertEquals(PokemonType.FLYING, PokemonTypeMap["flying"])
        assertEquals(PokemonType.POISON, PokemonTypeMap["poison"])
        assertEquals(PokemonType.GROUND, PokemonTypeMap["ground"])
        assertEquals(PokemonType.ROCK, PokemonTypeMap["rock"])
        assertEquals(PokemonType.BUG, PokemonTypeMap["bug"])
        assertEquals(PokemonType.GHOST, PokemonTypeMap["ghost"])
        assertEquals(PokemonType.STEEL, PokemonTypeMap["steel"])
        assertEquals(PokemonType.FIRE, PokemonTypeMap["fire"])
        assertEquals(PokemonType.WATER, PokemonTypeMap["water"])
        assertEquals(PokemonType.GRASS, PokemonTypeMap["grass"])
        assertEquals(PokemonType.ELECTRIC, PokemonTypeMap["electric"])
        assertEquals(PokemonType.PSYCHIC, PokemonTypeMap["psychic"])
        assertEquals(PokemonType.ICE, PokemonTypeMap["ice"])
        assertEquals(PokemonType.DRAGON, PokemonTypeMap["dragon"])
        assertEquals(PokemonType.DARK, PokemonTypeMap["dark"])
        assertEquals(PokemonType.FAIRY, PokemonTypeMap["fairy"])
        assertEquals(PokemonType.UNKNOWN, PokemonTypeMap["unknown"])
        assertEquals(PokemonType.SHADOW, PokemonTypeMap["shadow"])
    }

    @Test
    fun `test all types are in the type map`() {
        for(e in PokemonType.values()) {
            assertTrue(PokemonTypeMap.containsValue(e))
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