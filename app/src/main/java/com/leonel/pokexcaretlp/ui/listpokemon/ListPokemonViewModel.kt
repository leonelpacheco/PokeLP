package com.leonel.pokexcaretlp.ui.listpokemon

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.database.entities.toDataBase
import com.leonel.pokexcaretlp.model.PokemonListResponse
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.model.add
import com.leonel.pokexcaretlp.repository.PokemonRepository
import com.leonel.pokexcaretlp.utils.NetworkUtils
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val repository: PokemonRepository, @ApplicationContext context : Context): ViewModel() {

    val pokemonesLiveData = MutableLiveData<List<PokemonResult>>()
    val isLoading = MutableLiveData<Boolean>()
    fun getPokemones(isInternet: Boolean){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result= consultPomekemones(isInternet)
            if (!result.isNullOrEmpty()) {
                pokemonesLiveData.postValue(result)
                isLoading.postValue(false)
            }
            else
                isLoading.postValue(false)
        }
    }

    //**********Caso de Uso*************************
    private suspend fun consultPomekemones(isInternet: Boolean): List<PokemonResult>{
       if(isInternet) {
           val pokemones = repository.getPokemones()
           return if (pokemones.isNotEmpty()) {
               repository.clearPokemones()
               repository.insertPokemones(pokemones.map { it.toDataBase() })
               pokemones
           } else {
               return repository.getAllPokemonesFromDatabase()
           }
       }
        else
        return repository.getAllPokemonesFromDatabase()

    }
}