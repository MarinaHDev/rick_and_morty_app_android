package com.example.rick_and_morty_vol2.data.remote

import retrofit2.http.Query
import com.example.rick_and_morty_vol2.data.model.Character
import com.example.rick_and_morty_vol2.data.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}
