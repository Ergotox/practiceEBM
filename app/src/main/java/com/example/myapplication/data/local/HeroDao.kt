package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.HeroEntity

@Dao
interface HeroDao {
    @Insert
    fun save(hero: HeroEntity)

    @Delete
    fun delete(hero: HeroEntity)

    @Query("select * from heroes")
    fun getAll(): List<HeroEntity>

    @Query("select * from heroes where id = :id")
    fun getById(id: String): HeroEntity?
}