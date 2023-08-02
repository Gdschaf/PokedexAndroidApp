package com.radhangs.pokedexapp.shared

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.radhangs.pokedexapp.R

fun getDominantColor(context: Context, drawableId: Int): Color {
    val drawable = context.getDrawable(drawableId)
    return drawable?.let {
        val palette = Palette.from(drawable.toBitmap()).generate()
        val dominantColor = palette.getDominantColor(Color.Transparent.toArgb())
        Color(dominantColor)
    } ?: Color(context.getColor(R.color.primary_color))
}

fun String.capitalizeFirstLetter() : String = this.replaceFirstChar { char -> char.uppercase() }

fun String.ConvertToTitle(): String = this.split("-").joinToString(" ") { it.capitalizeFirstLetter() }