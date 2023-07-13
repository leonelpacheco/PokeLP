package com.leonel.pokexcaretlp.network


import com.leonel.pokexcaretlp.model.PokemonResponse
import com.leonel.pokexcaretlp.model.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class service @Inject constructor(private val api:apiClient) {

    suspend fun getPokemones(): List<PokemonResult> {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemones()
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getPokemonById(id: String): PokemonResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getPokemon(id)
            response.body()!!
        }
    }

}