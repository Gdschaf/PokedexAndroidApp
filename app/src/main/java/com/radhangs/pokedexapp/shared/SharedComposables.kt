package com.radhangs.pokedexapp.shared

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.PokemonTypesPresentationModel

// used on both the pokedex and pokemon detail's screen
@Composable
fun PokemonTitle(pokemonName: String, pokemonTypes: PokemonTypesPresentationModel, modifier: Modifier) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LargeText(text = pokemonName)
        PokemonTypes(pokemonTypes)
    }
}

// used by the PokemonTitle above
@Composable
fun PokemonTypes(pokemonTypes: PokemonTypesPresentationModel) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        val typeContentDescription = stringResource(id = R.string.type_content_description)
        Image (
            painter = painterResource(pokemonTypes.mainType.typeIconResourceId),
            contentDescription = stringResource(id = pokemonTypes.mainType.typeStringResourceId) + typeContentDescription,
            modifier = Modifier.size(dimensionResource(id = R.dimen.type_icon_size)),
        )
        pokemonTypes.secondaryType?.let { type ->
            Image (
                painter = painterResource(type.typeIconResourceId),
                contentDescription = "${stringResource(id = type.typeStringResourceId)} $typeContentDescription",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(dimensionResource(id = R.dimen.type_icon_size)),
            )
        }
    }
}

// used on the pokedex screen as well as the pokemon's evolution chain on the detail screen
@Composable
fun PokedexSprite(spriteUri: String?, modifier: Modifier, contentDescription: String) {
    // the sprite paths are honestly, a bit wack.
    // this could be better but with how odd it is, I'm not sure it matters.
    val url = spriteUri?.replace("/media", Constants.SPRITE_DOMAIN) ?: Constants.SPRITE_UNKNOWN_URL

    ImageFromUrl(
        url = url,
        modifier = modifier,
        contentDescription = contentDescription
    )
}

// generic helper image with loading from url
@Composable
fun ImageFromUrl(url: String, modifier: Modifier, contentDescription: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

// generic helper image with bitmap that's already loaded either locally or via coil
@Composable
fun ImageFromBitmap(bitmap: Bitmap?, modifier: Modifier, contentDescription: String) {
    if(bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = modifier
        )
    } else {
        Box(modifier = modifier) { }
    }
}

// generic vertical text element, only used in one place, but it's generic enough
@Composable
fun VerticalText(text: String, modifier: Modifier, style: TextStyle, description: String = text)
{
    Column(
        modifier = modifier.semantics { contentDescription = description },
        verticalArrangement = Arrangement.Center,
    ) {
        for(char in text) {
            Text(text = char.toString(), style = style)
        }
    }
}