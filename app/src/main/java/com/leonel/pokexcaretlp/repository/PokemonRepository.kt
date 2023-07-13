package com.leonel.pokexcaretlp.repository

import com.leonel.pokexcaretlp.database.dao.PokemonDao
import com.leonel.pokexcaretlp.database.entities.PokemonEntity
import com.leonel.pokexcaretlp.model.PokemonResponse
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.model.add
import com.leonel.pokexcaretlp.network.service
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: service, private val pokemonDao: PokemonDao) {

    suspend fun getPokemones(): List<PokemonResult> {
        return api.getPokemones()
    }

    suspend fun getPokemonById(id: String): PokemonResponse {
        return api.getPokemonById(id)
    }

    suspend fun getAllPokemonesFromDatabase():List<PokemonResult>{
        val response: List<PokemonEntity> = pokemonDao.getAllPokemon()
        return response.map { it.add() }
    }

    suspend fun insertPokemones(pokemones:List<PokemonEntity>){
        pokemonDao.insertAllPokemones(pokemones)
    }
    suspend fun clearPokemones(){
        pokemonDao.deleteAllPokemon()
    }

}