package com.leonel.pokexcaretlp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leonel.pokexcaretlp.database.dao.PokemonDao
import com.leonel.pokexcaretlp.database.entities.PokemonEntity


@Database(entities = [PokemonEntity::class], version = 1)
abstract class PokeDatabase: RoomDatabase()  {
    abstract fun getPokemonesDao(): PokemonDao
}