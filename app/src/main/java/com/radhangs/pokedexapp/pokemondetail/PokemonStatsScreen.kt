package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.EvolutionChainPresentationModel
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.Constants.MAX_STAT_VALUE
import com.radhangs.pokedexapp.shared.ImageFromUrl

@Composable
fun PokemonStats(details: PokemonDetailPresentationModel, modifier: Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            LabelValueStat("Height", details.height.toString() + "m", Modifier.weight(0.5f))
            LabelValueStat("Weight", details.weight.toString() + "kg", Modifier.weight(0.5f), Arrangement.End)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            LabelValueStat("Base Happiness", details.baseHappiness.toString(), Modifier.weight(0.5f))
            LabelValueStat("Capture Rate", details.captureRate.toString(), Modifier.weight(0.5f), Arrangement.End)
        }

        EvolutionChain(chain = details.evolutionaryChain)
        BaseStats(stats = details.baseStats)
    }
}

@Composable
fun LabelValueStat(label: String, value: String, modifier: Modifier, arrangement: Arrangement.Horizontal = Arrangement.Start) {
    val textStyle = TextStyle(fontSize = 18.sp, color = colorResource(id = R.color.text_color))
    Row (
        modifier = modifier,
        horizontalArrangement = arrangement
    ){
        Text(
            text = "$label:",
            style = textStyle,
            modifier = Modifier.padding(end = 8.dp) //todo add space to dimen
        )
        Text(
            text = value,
            style = textStyle
        )
    }
}

@Composable
fun EvolutionChain(chain: List<EvolutionChainPresentationModel>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp)
    ) {
        for (evo in chain)
        {
            val url = evo.spriteUri?.replace("/media", Constants.SPRITE_DOMAIN) ?: Constants.SPRITE_UNKNOWN_URL
            ImageFromUrl(
                url = url,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.sprite_size))
                    .weight(1.0f / chain.count()),
                contentDescription = "Evolution Sprite"
            )
        }
    }
}

@Composable
fun BaseStats(stats: Map<String, Int>) {
    for(stat in stats) {
        statDisplay(label = stat.key, value = stat.value, fillColor = Color.Black, emptyColor = Color.White)
    }
}

@Composable
fun statDisplay(label: String, value: Int, fillColor: Color, emptyColor: Color)
{
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.weight(0.5f),
            style = TextStyle(fontSize = 18.sp, color = colorResource(id = R.color.text_color))
        )
        Row(
            modifier = Modifier.fillMaxSize().weight(0.5f)
        ) {
            val weight = (value.toFloat() / MAX_STAT_VALUE).coerceIn(0.0f, 1.0f)
            Box(
                modifier = Modifier
                    .weight(weight)
                    .background(fillColor)
                    .height(15.dp),
            )
            Box(
                modifier = Modifier
                    .weight(1.0f - weight)
                    .background(emptyColor)
                    .height(15.dp)
            )
        }
    }
}