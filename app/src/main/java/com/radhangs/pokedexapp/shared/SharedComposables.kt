package com.radhangs.pokedexapp.shared

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Text(
            text = pokemonName,
            style = TextStyle(fontSize = 24.sp, color = colorResource(id = R.color.text_color)), // todo fine a place to define text sizes that isn't the dimen file
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
        if(pokemonTypes.secondaryType != null)
        {
            Image (
                painter = painterResource(pokemonTypes.secondaryType.typeIconResourceId),
                contentDescription = "${stringResource(id = pokemonTypes.secondaryType.typeStringResourceId)} $typeContentDescription",
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

// same loading ui for both screens
@Composable
fun Loading() {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(id = R.dimen.loading_progress_size)),
            // color = colorResource(id = R.color.primary_color) //kinda silly, kinda cool, idk
        )
        Text(
            text = stringResource(id = R.string.loading_text),
            modifier = Modifier.padding(start = 16.dp), // todo padding dimen again...
            style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 25.sp) // todo also font text size?
        )
    }
}

// same error try again ui for both screens
@Composable
fun ErrorTryAgain(onRetryClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        // show an exclamation point
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.alert_circle),
            modifier = Modifier.size(dimensionResource(id = R.dimen.error_icon_size)),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary_color))
        )
        // show an error: something went wrong
        Text(
            text = stringResource(id = R.string.error_text),
            style = TextStyle(fontSize = 20.sp, color = colorResource(id = R.color.text_color)), // todo, yeah yeah font text size, i know
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // TODO some padding dimen something something
        )
        // show a retry button, figure out callbacks for that
        // todo we could spice up this button a bit, no?
        Button(onClick = { onRetryClicked() } ) {
            Text(
                text = stringResource(id = R.string.retry)
            )
        }
    }
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