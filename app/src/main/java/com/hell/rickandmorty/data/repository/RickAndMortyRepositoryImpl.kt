package com.hell.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hell.rickandmorty.data.dto.CharacterResponse
import com.hell.rickandmorty.data.network.RickAndMortyApi
import com.hell.rickandmorty.data.network.RickAndMortyPagingSource
import com.hell.rickandmorty.domain.mapper.CharacterMapper
import com.hell.rickandmorty.domain.mapper.LocationMapper
import com.hell.rickandmorty.domain.model.Character
import com.hell.rickandmorty.domain.model.Location
import com.hell.rickandmorty.util.NetworkResponse
import com.hell.rickandmorty.util.SafeApiCall.safeApiCall
import com.hell.rickandmorty.util.parseIds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val characterMapper: CharacterMapper,
    private val locationMapper: LocationMapper
) : RickAndMortyRepository {

    override fun getCharacters(): Flow<NetworkResponse<List<Character>>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = safeApiCall { rickAndMortyApi.getCharacters() }) {
                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.errorMessage))
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(characterMapper.toDomainList(result.data.results)))
                }

                else -> Unit
            }
        }
    }

    override fun getCharacter(id: String): Flow<NetworkResponse<Character>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = safeApiCall { rickAndMortyApi.getCharacter(id) }) {
                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.errorMessage))
                is NetworkResponse.Success -> {
                    emit(NetworkResponse.Success(characterMapper.mapToDomainModel(result.data)))
                }

                else -> Unit
            }
        }
    }

    override fun getMultipleCharacters(urls: List<String>): Flow<NetworkResponse<List<Character>>> {
        return flow {
            emit(NetworkResponse.Loading)

            when (val result = safeApiCall {
                rickAndMortyApi.getMultipleCharacters(urls.parseIds())
            }) {
                is NetworkResponse.Error -> emit(NetworkResponse.Error(result.errorMessage))
                is NetworkResponse.Success -> {
                    /*
                         * This logic checks the response data and if Json is not an array
                         * transforms it into an array. Because some locations have only one
                         * Character while others have more than one.
                         */
                    val gson = Gson()
                    if (result.data is List<*>) {
                        val characterResponseLists: List<CharacterResponse> = gson.fromJson(
                            gson.toJson(result.data),
                            object : TypeToken<List<CharacterResponse>>() {}.type
                        )
                        emit(
                            NetworkResponse.Success(
                                characterMapper.toDomainList(characterResponseLists)
                            )
                        )
                    } else {
                        val characterResponse =
                            gson.fromJson(gson.toJson(result.data), CharacterResponse::class.java)
                        emit(
                            NetworkResponse.Success(
                                characterMapper.toDomainList(listOf(characterResponse))
                            )
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    override fun getLocations(): Flow<PagingData<Location>> {
        return Pager(PagingConfig(DEFAULT_PAGE_SIZE)) {
            RickAndMortyPagingSource(locationMapper) { page ->
                rickAndMortyApi.getLocations(page)
            }
        }.flow
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}