package com.radhangs.pokedexapp.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.radhangs.pokedexapp.R

// same error try again ui for both screens
@Composable
fun ErrorTryAgain(onRetryClicked: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val defaultGap = dimensionResource(id = R.dimen.default_gap)
        // show an exclamation point image
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.alert_circle),
            modifier = Modifier.size(dimensionResource(id = R.dimen.error_icon_size)),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary_color))
        )
        // show an error: something went wrong
        LargeText(
            text = stringResource(id = R.string.error_text),
            modifier = Modifier.padding(top = defaultGap, bottom = defaultGap)
        )
        // show a retry button
        Button(onClick = { onRetryClicked() } ) {
            Text(
                text = stringResource(id = R.string.retry)
            )
        }
    }
}