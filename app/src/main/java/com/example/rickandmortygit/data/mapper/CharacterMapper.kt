package com.example.rick_and_morty_vol2.data.mapper

import com.example.rick_and_morty_vol2.data.local.CharacterEntity
import com.example.rick_and_morty_vol2.data.model.Character

fun Character.toEntity(isFavorite: Boolean = false): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
        isFavorite = isFavorite,
        shortDescription = "$species - $status"
    )
}

fun CharacterEntity.toCharacter(): com.example.rick_and_morty_vol2.data.model.Character {
    return com.example.rick_and_morty_vol2.data.model.Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = "",
        gender = "",
        origin = com.example.rick_and_morty_vol2.data.model.Origin("",""),
        location = com.example.rick_and_morty_vol2.data.model.Location("", ""),
        image = image,
        episode = emptyList(),
        url = "",
        created = ""
    )
}
