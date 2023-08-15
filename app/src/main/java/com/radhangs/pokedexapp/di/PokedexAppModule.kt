package com.radhangs.pokedexapp.di

import android.content.Context
import coil.imageLoader
import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import com.radhangs.pokedexapp.shared.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexAppModule {

    @Provides
    @Singleton
    fun provideApolloClient() = ApolloClient.Builder()
        .serverUrl(Constants.POKEAPI_GRAPHQL_URL)
        .build()

    @Provides
    @Singleton
    fun provideSharedSelectedPokemonRepository() = SelectedPokemonRepository()

    /* Coil performs best when there's a single ImageLoader shared throughout the app
    *  an included singleton is provided from the io.coil-kt:coil artifact and
    *  accessed using the extension function below.
    *  more info can be found here: https://coil-kt.github.io/coil/image_loaders/ */
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context) = context.imageLoader
}
