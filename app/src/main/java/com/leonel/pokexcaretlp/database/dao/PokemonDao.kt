package com.leonel.pokexcaretlp.database.dao

import androidx.room.*
import com.leonel.pokexcaretlp.database.entities.PokemonEntity
import com.leonel.pokexcaretlp.utils.Constant


@Dao
interface PokemonDao {

    @Query("SELECT * FROM ${Constant.TABLE_POKEMON} ORDER BY id ASC")
    suspend fun getAllPokemon():List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemones(pokemones:List<PokemonEntity>)

    @Query("DELETE FROM ${Constant.TABLE_POKEMON}")
    suspend fun deleteAllPokemon()

}