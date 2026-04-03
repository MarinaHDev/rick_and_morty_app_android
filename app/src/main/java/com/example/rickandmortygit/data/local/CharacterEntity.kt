package com.example.rick_and_morty_vol2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val isFavorite: Boolean = false,
    val shortDescription: String? = null
)
