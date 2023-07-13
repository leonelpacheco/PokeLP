package com.leonel.pokexcaretlp.model

import android.os.Parcelable
import com.leonel.pokexcaretlp.database.entities.PokemonEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data  class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val stats: List<Stats>,
    val height: Int,
    val weight: Int
) : Parcelable
