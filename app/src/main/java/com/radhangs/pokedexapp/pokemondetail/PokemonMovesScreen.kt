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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.radhangs.pokedexapp.model.PokemonMovePresentationModel
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.DamageCategoryPresentationModel
import com.radhangs.pokedexapp.model.PokemonTypeWithResources
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
            PokemonMoveTitleRow(
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
            PokemonMoveDetailsRow(
                pp = item.pp,
                power = item.power,
                accuracy = item.accuracy,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun PokemonMoveTitleRow(
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
        Image (
            painter = painterResource(moveType.typeIconResourceId),
            contentDescription = stringResource(id = moveType.typeStringResourceId) + stringResource(id = R.string.type_content_description),
            modifier = Modifier.padding(start = 4.dp).size(20.dp)
        )
        Text(
            text = moveName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 18.sp), // todo, define text style somewhere?
            modifier = Modifier.weight(1f).fillMaxWidth()
        )
        Image (
            painter = painterResource(damageType.drawableResourceId),
            contentDescription = stringResource(id = damageType.stringResourceId) + stringResource(id = R.string.damage_category_content_description),
            modifier = Modifier.padding(end = 4.dp).size(25.dp)
        )
    }
}

@Composable
fun PokemonMoveDetailsRow(pp: Int, power: Int?, accuracy: Int, modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        PokemonMoveDetail(
            valueLabel = "PP", // todo, add to strings value resource
            valueString = pp.toString(),
            modifier = Modifier.weight(1.0f)
        )
        PokemonMoveDetail(
            valueLabel = "Power", // todo, add to strings value resource
            valueString = getValueString(power),
            modifier = Modifier.weight(1.0f)
        )
        PokemonMoveDetail(
            valueLabel = "Accuracy", // todo, add to strings value resource
            valueString = accuracy.toString(),
            modifier = Modifier.weight(1.0f)
        )
    }
}

@Composable
fun MoveDetailText(text: String) {
    Text(
        text = text,
        style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 16.sp), // todo, define this text style somewhere?
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun PokemonMoveDetail(valueLabel: String, valueString: String, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoveDetailText(text = valueLabel)
        MoveDetailText(text = valueString)
    }
}