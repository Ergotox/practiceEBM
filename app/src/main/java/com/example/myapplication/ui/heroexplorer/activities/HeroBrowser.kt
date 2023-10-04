package com.example.myapplication.ui.heroexplorer.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.model.Hero
import com.example.myapplication.repository.HeroRepository
import com.example.myapplication.ui.heroexplorer.components.HeroList
import com.example.myapplication.ui.heroexplorer.components.SearchBar
import com.example.myapplication.ui.heroexplorer.components.informative.ErrorMessage
import com.example.myapplication.ui.heroexplorer.components.informative.Loader
import com.example.myapplication.ui.heroexplorer.components.informative.WaitingMessage
import com.example.myapplication.utils.Result

@Composable
fun HeroBrowser() {
    val context = LocalContext.current
    val (searchTerm, setSearchTerm) = remember { mutableStateOf("") }
    val (heroes, setHeroes) = remember { mutableStateOf<List<Hero>?>(listOf<Hero>()) }
    val (isLoading, setIsLoading) = remember { mutableStateOf(false) }
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val heroRepository = HeroRepository(heroDao = heroDao)


    LaunchedEffect(searchTerm) {
        setIsLoading(true)

        heroRepository.searchByName(searchTerm) { result ->
            if (result is Result.Success) {
                result.data!!.forEach { hero ->
                    hero.isFavorite = heroRepository.existsById(hero)
                }
                setHeroes(result.data!!)
            }
            else {
                setHeroes(null)
            }

            setIsLoading(false)
        }
    }

    if (heroes == null) {
        ErrorMessage()
        return
    }

    Column {
        SearchBar(setSearchTerm)

        if (searchTerm.isEmpty()) {
            WaitingMessage()
            return
        }

        if (isLoading) {
            Loader()
            return
        }

        HeroList(heroes)
    }
}