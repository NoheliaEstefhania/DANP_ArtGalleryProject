package com.example.danp_artgallery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danp_artgallery.data.model.Exposition
import com.example.danp_artgallery.data.model.Painting
import com.example.danp_artgallery.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpositionViewModel : ViewModel() {
    private val _expositions = MutableLiveData<List<Exposition>>(emptyList())
    val expositions: LiveData<List<Exposition>> = _expositions

    private val _selectedExposition = MutableLiveData<Exposition?>()
    val selectedExposition: LiveData<Exposition?> = _selectedExposition

    private val _selectedExpositionPictures = MutableLiveData<Map<Int,List<Painting?>>>()
    val selectedExpositionPictures: LiveData<Map<Int,List<Painting?>>> = _selectedExpositionPictures

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

    fun fetchPictures(expositionId: Int) {
        viewModelScope.launch {
            try {
                //_selectedExpositionPictures.value = RetrofitClient.apiService.getPictures(expositionId)
                val pictures = RetrofitClient.apiService.getPictures(expositionId)
                val currentPictures = _selectedExpositionPictures.value.orEmpty().toMutableMap()
                currentPictures[expositionId] = pictures
                _selectedExpositionPictures.value = currentPictures
            } catch (e: Exception) {
                // Maneja el error
                Log.e("viewmodel","fallo en la api de pinturas")
            }
        }
    }

    fun fetchExpositionDetails(expositionId: Int) {
        viewModelScope.launch {
            try {
                _selectedExposition.value = RetrofitClient.apiService.getExpositionDetails(expositionId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}