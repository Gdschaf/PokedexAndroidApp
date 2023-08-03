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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.EvolutionChainPresentationModel
import com.radhangs.pokedexapp.model.PokemonDetailPresentationModel
import com.radhangs.pokedexapp.shared.Constants
import com.radhangs.pokedexapp.shared.Constants.MAX_STAT_VALUE
import com.radhangs.pokedexapp.shared.ConvertToTitle
import com.radhangs.pokedexapp.shared.ImageFromUrl
import com.radhangs.pokedexapp.shared.PokedexSprite

// all the pokemon stats under the titles and above the moves
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
            LabelValueStat(
                stringResource(id = R.string.height_label),
                value = details.height.toString() + "m",
                Modifier.weight(0.5f)
            )
            LabelValueStat(
                stringResource(id = R.string.weight_label),
                value = details.weight.toString() + "kg",
                Modifier.weight(0.5f),
                Arrangement.End
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            LabelValueStat(
                stringResource(id = R.string.base_happiness_label),
                value = details.baseHappiness.toString(),
                Modifier.weight(0.5f)
            )
            LabelValueStat(
                stringResource(id = R.string.capture_rate_label),
                value = details.captureRate.toString(),
                Modifier.weight(0.5f),
                Arrangement.End
            )
        }

        EvolutionChain(chain = details.evolutionaryChain)
        BaseStats(stats = details.baseStats)
    }
}

// used to display the core stat labels/values
@Composable
fun LabelValueStat(label: String, value: String, modifier: Modifier, arrangement: Arrangement.Horizontal = Arrangement.Start) {
    val textStyle = TextStyle(fontSize = 18.sp, color = colorResource(id = R.color.text_color)) // todo, define this text style somewhere
    Row (
        modifier = modifier,
        horizontalArrangement = arrangement
    ){
        Text(
            text = "$label:",
            style = textStyle,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.label_value_gap))
        )
        Text(
            text = value,
            style = textStyle
        )
    }
}

// the row of all the pokemon sprites in an evolutionary chain
// reuses the PokedexSprite composable used on the main/pokedex screen
@Composable
fun EvolutionChain(chain: List<EvolutionChainPresentationModel>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = dimensionResource(id = R.dimen.default_gap))
    ) {
        for (evo in chain)
        {
            PokedexSprite(
                spriteUri = evo.spriteUri,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.sprite_size))
                    .weight(1.0f / chain.count()),
                contentDescription = stringResource(id = R.string.pokemon_evolution_sprite_content_description)
            )
        }
    }
}

// map of base stat labels to their respective colors, could find a better home for this
val statColorMap = mapOf(
    "hp" to R.color.hp_foreground,
    "attack" to R.color.attack_foreground,
    "defense" to R.color.defense_foreground,
    "special-attack" to R.color.special_attack_foreground,
    "special-defense" to R.color.special_defense_foreground,
    "speed" to R.color.speed_foreground
)

// all of the base stats, their labels, and bar graph.
// todo, add base stat values
@Composable
fun BaseStats(stats: Map<String, Int>) {
    for(stat in stats) {
        StatDisplay(
            label = stat.key.ConvertToTitle(),
            value = stat.value,
            maxValue = MAX_STAT_VALUE,
            fillColor = statColorMap[stat.key]?.let { colorResource(id = it) } ?: Color.Black,
            emptyColor = Color.White
        )
    }
}

// bar graph display used to display base stats but could very well be used for other things
@Composable
fun StatDisplay(label: String, value: Int, maxValue: Int, fillColor: Color, emptyColor: Color)
{
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.weight(0.5f),
            style = TextStyle(fontSize = 18.sp, color = colorResource(id = R.color.text_color)) // todo define this text style somewhere
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f)
        ) {
            val weight = (value.toFloat() / maxValue).coerceIn(0.0f, 1.0f)
            val barHeight = dimensionResource(id = R.dimen.stat_bar_height)
            Box(
                modifier = Modifier
                    .weight(weight)
                    .background(fillColor)
                    .height(barHeight),
            )
            Box(
                modifier = Modifier
                    .weight(1.0f - weight)
                    .background(emptyColor)
                    .height(barHeight)
            )
        }
    }
}