package com.hell.rickandmorty.data.repository

import androidx.paging.PagingData
import com.hell.rickandmorty.domain.model.Character
import com.hell.rickandmorty.domain.model.Location
import com.hell.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<NetworkResponse<List<Character>>>
    fun getCharacter(id: String): Flow<NetworkResponse<Character>>
    fun getMultipleCharacters(urls: List<String>): Flow<NetworkResponse<List<Character>>>
    fun getLocations(): Flow<PagingData<Location>>
}