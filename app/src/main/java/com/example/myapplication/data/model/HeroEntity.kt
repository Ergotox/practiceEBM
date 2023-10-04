package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey val id: String
)