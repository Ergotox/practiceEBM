package com.example.myapplication.ui.heroexplorer.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.model.Hero
import com.example.myapplication.repository.HeroRepository
import com.example.myapplication.ui.heroexplorer.components.HeroDetailBiography
import com.example.myapplication.ui.heroexplorer.components.HeroDetailHeader
import com.example.myapplication.ui.heroexplorer.components.HeroDetailPowerStats
import com.example.myapplication.ui.heroexplorer.components.informative.ErrorMessage
import com.example.myapplication.ui.heroexplorer.components.informative.Loader
import com.example.myapplication.utils.Result


@Composable
fun HeroDetail(id: String) {
    val context = LocalContext.current
    val (hero, setHero) = remember { mutableStateOf<Hero?>(null) }
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
    val heroDao = AppDatabase.getInstance(context).heroDao()
    val heroRepository = HeroRepository(heroDao = heroDao)

    heroRepository.searchById(id) { result ->
        if (result is Result.Success) {
            setHero(result.data!!)
        }
        setIsLoading(false)
    }


    if (isLoading) {
        Loader()
        return
    }

    if (hero == null) {
        ErrorMessage()
        return
    }

    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
        HeroDetailHeader(hero)
        HeroDetailBiography(hero)
        HeroDetailPowerStats(hero)
    }
}