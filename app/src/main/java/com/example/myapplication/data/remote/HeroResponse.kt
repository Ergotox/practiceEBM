package com.example.myapplication.data.remote

import com.example.myapplication.data.model.Hero
import com.google.gson.annotations.SerializedName

data class HeroResponse(
    @SerializedName("results")
    val heroes: List<Hero>
)
