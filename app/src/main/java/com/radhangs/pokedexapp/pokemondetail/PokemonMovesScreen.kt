package com.radhangs.pokedexapp.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.DamageCategoryPresentationModel
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel
import com.radhangs.pokedexapp.model.PokemonTypeWithResources
import com.radhangs.pokedexapp.shared.MediumText
import com.radhangs.pokedexapp.shared.SmallText
import com.radhangs.pokedexapp.shared.getValueString

@Composable
fun MoveCard(item: PokemonMovePresentationModel) {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.background_primary)
        )
    ) {
        Column {
            PokemonMoveTitle(
                moveName = item.moveName,
                moveType = item.type,
                damageType = item.damageType,
                modifier = Modifier.padding(4.dp)
            )
            Divider(
                thickness = Dp.Hairline,
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )
            PokemonMoveDetails(
                pp = item.pp,
                power = item.power,
                accuracy = item.accuracy,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun PokemonMoveTitle(
    moveName: String,
    moveType: PokemonTypeWithResources,
    damageType: DamageCategoryPresentationModel,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(moveType.typeIconResourceId),
            contentDescription = stringResource(id = moveType.typeStringResourceId) +
                stringResource(id = R.string.type_content_description),
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp)
        )
        MediumText(
            text = moveName,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(damageType.drawableResourceId),
            contentDescription = stringResource(id = damageType.stringResourceId) +
                stringResource(id = R.string.damage_category_content_description),
            modifier = Modifier
                .padding(end = 4.dp)
                .size(25.dp)
        )
    }
}

@Composable
fun PokemonMoveDetails(pp: Int, power: Int?, accuracy: Int, modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        // power is nullable meaning some moves don't have a power
        // this will say "no power" rather then "power dash" in the case of a move with no power
        val powerContentDescription = if (power != null) {
            "${stringResource(id = R.string.move_power_label)} $power"
        } else {
            stringResource(id = R.string.move_no_power_content_description)
        }

        PokemonMoveStat(
            valueLabel = stringResource(id = R.string.move_pp_label),
            valueString = pp.toString(),
            modifier = Modifier.weight(1.0f)
        )
        PokemonMoveStat(
            valueLabel = stringResource(id = R.string.move_power_label),
            valueString = getValueString(power),
            modifier = Modifier
                .weight(1.0f)
                .semantics { contentDescription = powerContentDescription }
        )
        PokemonMoveStat(
            valueLabel = stringResource(id = R.string.move_accuracy_label),
            valueString = accuracy.toString(),
            modifier = Modifier.weight(1.0f)
        )
    }
}

@Composable
fun PokemonMoveStat(valueLabel: String, valueString: String, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoveStatText(text = valueLabel)
        MoveStatText(text = valueString)
    }
}

@Composable
fun MoveStatText(text: String) {
    SmallText(
        text = text
    )
}
