package com.example.rick_and_morty_vol2.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rick_and_morty_vol2.data.local.CharacterDao
import com.example.rick_and_morty_vol2.data.local.CharacterEntity
import com.example.rick_and_morty_vol2.data.remote.RickAndMortyApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
    fun getCharactersPager(query: String? = null): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { dao.getAllCharacters() }
        ).flow
    }

    suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
        dao.updateFavorite(id, isFavorite)
    }

    suspend fun getCharacterDetail(id: Int): com.example.rick_and_morty_vol2.data.model.Character {
        return api.getCharacterById(id)
    }

    suspend fun refreshCharacters(characters: List<CharacterEntity>) {
        dao.insertAll(characters)
    }
}
