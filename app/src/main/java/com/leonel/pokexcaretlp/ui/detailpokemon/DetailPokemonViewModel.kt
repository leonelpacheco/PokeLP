package com.leonel.pokexcaretlp.ui.detailpokemon

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonel.pokexcaretlp.database.entities.toDataBase
import com.leonel.pokexcaretlp.model.PokemonResponse
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.repository.PokemonRepository
import com.leonel.pokexcaretlp.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(private val repository: PokemonRepository): ViewModel() {
    val pokemonLiveData = MutableLiveData<PokemonResponse>()
    val isLoading = MutableLiveData<Boolean>()

    fun getPokemon(id: String){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result= consultPomekemon(id)
            if (result != null) {
                pokemonLiveData.postValue(result)
                isLoading.postValue(false)
            }
            else
                isLoading.postValue(false)
        }
    }

    //**********Caso de Uso*************************
    private suspend fun consultPomekemon(id: String): PokemonResponse? {
                return repository.getPokemonById(id)
    }
}