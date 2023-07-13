package com.leonel.pokexcaretlp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user") var user: String,
    @SerializedName("password") var password: String
)
