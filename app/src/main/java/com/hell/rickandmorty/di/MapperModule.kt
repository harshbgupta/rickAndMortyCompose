package com.hell.rickandmorty.di

import com.hell.rickandmorty.data.dto.CharacterResponse
import com.hell.rickandmorty.data.dto.LocationResponse
import com.hell.rickandmorty.domain.mapper.CharacterMapper
import com.hell.rickandmorty.domain.mapper.DomainMapper
import com.hell.rickandmorty.domain.mapper.LocationMapper
import com.hell.rickandmorty.domain.model.Character
import com.hell.rickandmorty.domain.model.Location
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    @ViewModelScoped
    abstract fun bindCharacterMapper(characterMapper: CharacterMapper): DomainMapper<CharacterResponse, Character>

    @Binds
    @ViewModelScoped
    abstract fun bindLocationMapper(locationMapper: LocationMapper): DomainMapper<LocationResponse, Location>
}