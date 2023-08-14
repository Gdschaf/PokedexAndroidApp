package com.radhangs.pokedexapp.di

import com.apollographql.apollo3.ApolloClient
import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import com.radhangs.pokedexapp.shared.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}