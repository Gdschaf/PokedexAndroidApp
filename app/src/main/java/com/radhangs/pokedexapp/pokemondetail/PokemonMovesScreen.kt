package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.radhangs.pokedexapp.model.PokemonMovePresentationData
import com.radhangs.pokedexapp.model.getDrawableDamageTypeIcon
import com.radhangs.pokedexapp.model.getDrawableTypeIcon

@Composable
fun MoveHeader() {

}

@Composable
fun Move(item: PokemonMovePresentationData) {
    // make a row, lets copy serbii basically
    // do level, name, type icon, category icon, power, accuracy, pp
    // don't bother with the description at all
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = item.learnLevel.toString(), modifier = Modifier.weight(1.0f))
        Text(text = item.moveName, modifier = Modifier.weight(3.0f))
        Image (
            painter = painterResource(getDrawableTypeIcon(item.type)),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier.size(15.dp).weight(1.0f)
        )
        Image (
            painter = painterResource(getDrawableDamageTypeIcon(item.damageType)),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier.size(15.dp).weight(1.0f)
        )
        Text(text = item.power.toString(), modifier = Modifier.weight(1.0f))
        Text(text = item.accuracy.toString(), modifier = Modifier.weight(1.0f))
        Text(text = item.pp.toString(), modifier = Modifier.weight(1.0f))
    }
}