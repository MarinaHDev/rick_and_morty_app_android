package com.example.rick_and_morty_vol2.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rick_and_morty_vol2.data.model.Character
import com.example.rick_and_morty_vol2.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character.asStateFlow()

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getCharacterDetail(id)
                _character.value = result
            } catch (e: Exception) {
                // TODO: Handle error (show error state)
                e.printStackTrace()
            }
        }
    }
}