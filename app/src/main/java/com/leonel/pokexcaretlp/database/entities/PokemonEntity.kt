package com.leonel.pokexcaretlp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.utils.Constant


@Entity(tableName = Constant.TABLE_POKEMON)
data class PokemonEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image") val image: String
)

fun PokemonResult.toDataBase() = PokemonEntity(name= name, url = url, image = url)