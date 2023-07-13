package com.leonel.pokexcaretlp.model

import android.os.Parcelable
import com.leonel.pokexcaretlp.database.entities.PokemonEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResult(
    val name: String,
    val url: String
) : Parcelable

fun PokemonEntity.add()=PokemonResult(name,url)