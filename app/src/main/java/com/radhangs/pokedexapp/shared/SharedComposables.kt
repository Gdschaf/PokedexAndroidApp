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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.radhangs.pokedexapp.R
import com.radhangs.pokedexapp.model.PokemonTypesPresentationModel
import com.radhangs.pokedexapp.model.getDrawableTypeIcon

// TODO clean all this up, kthx.

// might wanna consider breaking this out into other files or moving things else where? better organization

@Composable
fun PokemonTitle(pokemonName: String, pokemonTypes: PokemonTypesPresentationModel, modifier: Modifier) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = pokemonName,
            style = TextStyle(fontSize = 24.sp, color = colorResource(id = R.color.text_color) // todo fine a place to define text sizes that isn't the dimen file
            ))
        PokemonTypes(pokemonTypes)
    }
}

@Composable
fun PokemonTypes(pokemonTypes: PokemonTypesPresentationModel) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Image (
            painter = painterResource(getDrawableTypeIcon(pokemonTypes.mainType)),
            contentDescription = "something type", // todo, add content description for the types
            modifier = Modifier.size(dimensionResource(id = R.dimen.type_icon_size)),
        )
        if(pokemonTypes.secondaryType != null)
        {
            Image (
                painter = painterResource(getDrawableTypeIcon(pokemonTypes.secondaryType)),
                contentDescription = "something type", // todo, add content description for the types
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(dimensionResource(id = R.dimen.type_icon_size)),
            )
        }
    }
}

@Composable
fun ImageFromUrl(url: String, modifier: Modifier, contentDescription: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

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

@Composable
fun BackIcon(
    modifier: Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.back_arrow_inverse),
            contentDescription = "Back",
            tint = LocalContentColor.current
        )
    }
}

// todo kind of wanna add a stroke around the sprite to make it look better? idk, try it.
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

@Composable
fun VerticalText(text: String, modifier: Modifier, style: TextStyle)
{
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        for(char in text) {
            Text(text = char.toString(), style = style)
        }
    }
}