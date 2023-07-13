package com.leonel.pokexcaretlp.network



import com.leonel.pokexcaretlp.model.PokemonListResponse
import com.leonel.pokexcaretlp.model.PokemonResponse
import com.leonel.pokexcaretlp.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface apiClient {
    @GET(Constant.POKEMON)
    suspend fun getPokemones(): Response<PokemonListResponse>

    @GET(Constant.POKEMON+"{id}/")
    suspend fun getPokemon(
        @Path("id") id: String
    ): Response<PokemonResponse>

}