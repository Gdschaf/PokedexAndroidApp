package com.radhangs.pokedexapp.di

import com.radhangs.pokedexapp.repository.SelectedPokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object PokemonDetailModule {
    @Provides
    @ViewModelScoped
    @Named("PokemonId")
    fun providePokemonId(
        selectedPokemonRepository: SelectedPokemonRepository
    ): Int = selectedPokemonRepository.getSelectedPokemon().value ?: 1
}
