package com.radhangs.pokedexapp.shared

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.radhangs.pokedexapp.R

fun getDominantColor(context: Context, drawableId: Int): Color {
    val drawable = context.getDrawable(drawableId)
    return drawable?.let {
        getDominantColorFromBitmap(drawable.toBitmap())
    } ?: Color(context.getColor(R.color.primary_color))
}

fun getDominantColorFromBitmap(bitmap: Bitmap) : Color
{
    val palette = Palette.from(bitmap).generate()
    val dominantColor = palette.getDominantColor(Color.Transparent.toArgb())
    return Color(dominantColor)
}

fun String.capitalizeFirstLetter() : String = this.replaceFirstChar { char -> char.uppercase() }

fun String.convertToTitle(): String = this.split("-").joinToString(" ") {
    it.capitalizeFirstLetter()
}

fun getValueString(value: Int?) = value?.toString() ?: "-"