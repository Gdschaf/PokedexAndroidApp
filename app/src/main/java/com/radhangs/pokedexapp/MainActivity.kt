package com.radhangs.pokedexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.radhangs.pokedexapp.ui.theme.PokedexAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.background_primary)
                ) {
                    // had to move this here, it was causing issues once it wasn't loading any more, it'd recreate the view model every time
                    // pulling this outside of that scope fixed it but it ain't perfect. there's a better way to do this
                    val pokedexViewModel = PokedexViewModel(apolloClient())
                    Pokedex(pokedexViewModel)
                }
            }
        }
    }
}