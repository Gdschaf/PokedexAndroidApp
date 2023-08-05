package com.radhangs.pokedexapp.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import com.radhangs.pokedexapp.R

// same loading ui for both screens
@Composable
fun Loading() {
    Row(
        //setting mergeDescendants to true makes the progress indicator not animate which I find odd
        modifier = Modifier.fillMaxSize().semantics(mergeDescendants = true) { },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(id = R.dimen.loading_progress_size)),
        )
        LargeText(
            text = stringResource(id = R.string.loading_text),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.default_gap)),
        )
    }
}