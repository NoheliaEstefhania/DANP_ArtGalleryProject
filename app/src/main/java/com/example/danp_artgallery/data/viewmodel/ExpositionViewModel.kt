package com.example.danp_artgallery.data.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danp_artgallery.data.model.Exposition
import com.example.danp_artgallery.data.model.Painting
import com.example.danp_artgallery.data.network.RetrofitClient
import kotlinx.coroutines.launch

class ExpositionViewModel : ViewModel() {
    private val _expositions = MutableLiveData<List<Exposition>>(emptyList())
    val expositions: LiveData<List<Exposition>> = _expositions

    init {
        fetchExpositions()
    }

    private fun fetchExpositions() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getExpositions(1) // Cambia el ID de la galería según corresponda
                _expositions.value = response
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }
}