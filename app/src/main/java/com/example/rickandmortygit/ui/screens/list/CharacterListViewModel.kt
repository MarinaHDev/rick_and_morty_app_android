package com.example.rick_and_morty_vol2.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rick_and_morty_vol2.data.local.CharacterEntity
import com.example.rick_and_morty_vol2.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("") //why we use _? what is a MutableStateFlow?
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)//what is this annotation?
    val charactersFlow: Flow<PagingData<CharacterEntity>> = _searchQuery
        .flatMapLatest { query ->//what is flatmaplatest?
            repository.getCharactersPager(query.ifBlank { null })
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavorite(character: CharacterEntity) {//what is toggle?
        viewModelScope.launch {
            repository.toggleFavorite(character.id, !character.isFavorite)
        }
    }

}