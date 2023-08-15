package com.radhangs.pokedexapp.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.radhangs.pokedexapp.R

// 16.sp is small
@Composable
fun SmallText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 16.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

// 18.sp is medium
@Composable
fun MediumText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 18.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

// 24.sp is large
@Composable
fun LargeText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyle(color = colorResource(id = R.color.text_color), fontSize = 24.sp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}
