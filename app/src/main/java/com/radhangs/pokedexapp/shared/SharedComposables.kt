package com.radhangs.pokedexapp.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.radhangs.pokedexapp.R

// reusable
@Composable
fun ImageFromUrl(url: String, modifier: Modifier, contentDescription: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}

// reusable on the other page as well
@Composable
fun Loading() {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp, 50.dp),
            // color = colorResource(id = R.color.primary_color) //kinda silly, kinda cool, idk
        ) //put into values resource file
        Text(
            text = "Loading...",
            modifier = Modifier.padding(start = 16.dp),
            style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 25.sp)
        )
    }
}

// also reusable
@Composable
fun ErrorTryAgain(onRetryClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        // show an exclamation point
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.alert_circle),
            modifier = Modifier.size(width = 100.dp, height = 100.dp), // TODO add this to the values xml
            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary_color))
        )
        // show an error: something went wrong
        Text(
            text = "Something went wrong.",
            style = TextStyle(fontSize = 20.sp, color = colorResource(id = R.color.text_color)),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp) // TODO add this to the values xml
        )
        // show a retry button, figure out callbacks for that
        Button(onClick = { onRetryClicked() } ) {
            Text(
                text = "Retry"
            )
        }
    }
}