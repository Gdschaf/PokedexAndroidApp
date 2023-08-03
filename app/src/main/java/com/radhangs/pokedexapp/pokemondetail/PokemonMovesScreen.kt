package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel.Companion.getDrawableDamageTypeIcon
import com.radhangs.pokedexapp.R

@Composable
fun MoveHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .semantics(mergeDescendants = true) { contentDescription = "Move header "}
    ) {
        val textStyle = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 16.sp) // todo define text style somewhere
        Text(text = "Lvl", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Learn level" })
        Text(text = "Name", style = textStyle, modifier = Modifier.weight(3.0f).semantics { contentDescription = "Move name" })
        Text(text = "Type", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Move type" }, textAlign = TextAlign.Center)
        Text(text = "Dmg", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Damage class" }, textAlign = TextAlign.Center)
        Text(text = "Atk", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Attack power" }, textAlign = TextAlign.Center)
        Text(text = "Acc", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Accuracy" }, textAlign = TextAlign.Center)
        Text(text = "Pp", style = textStyle, modifier = Modifier.weight(1.0f).semantics { contentDescription = "Number of power points" }, textAlign = TextAlign.Center)
    }
    Divider(color = colorResource(id = R.color.text_color), thickness = 2.dp, modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp))
}

fun getValueString(value: Int?) = value?.toString() ?: "-"

@Composable
fun Move(item: PokemonMovePresentationModel) {
    // make a row, lets copy serbii basically
    // do level, name, type icon, category icon, power, accuracy, pp
    // don't bother with the description at all
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .semantics(mergeDescendants = true) { }
    ) {
        val textStyle = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 14.sp) // todo define text style somewhere
        Text(text = item.learnLevel.toString(), modifier = Modifier
            .weight(1.0f)
            .semantics { contentDescription = "level learned, ${item.learnLevel}" }, style = textStyle)
        Text(text = item.moveName, modifier = Modifier.weight(3.0f), maxLines = 1, overflow = TextOverflow.Ellipsis, style = textStyle)
        Image (
            painter = painterResource(item.type.typeIconResourceId),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier
                .size(15.dp)
                .weight(1.0f)
        )
        Image (
            painter = painterResource(getDrawableDamageTypeIcon(item.damageType)),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier
                .weight(1.0f)
                .fillMaxHeight()
        )
        Text(
            text = getValueString(item.power),
            modifier = Modifier
                .weight(1.0f)
                .semantics {
                    contentDescription =
                        item.power?.let { "Attack ${item.power}" } ?: "No attack power"
                },
            textAlign = TextAlign.Center,
            style = textStyle
        )
        Text(
            text = item.accuracy.toString(),
            modifier = Modifier
                .weight(1.0f)
                .semantics { contentDescription = "Accuracy ${item.accuracy}" },
            textAlign = TextAlign.Center,
            style = textStyle
        )
        Text(
            text = item.pp.toString(),
            modifier = Modifier
                .weight(1.0f)
                .semantics { contentDescription = "Power points ${item.pp}" },
            textAlign = TextAlign.Center,
            style = textStyle
        )
    }
}