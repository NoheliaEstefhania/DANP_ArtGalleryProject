package com.example.danp_artgallery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danp_artgallery.data.model.Painting
import com.example.danp_artgallery.data.network.RetrofitClient
import kotlinx.coroutines.launch

class PaintingViewModel : ViewModel() {
    private val _paintings = mutableStateOf<List<Painting>>(emptyList())
    val paintings = _paintings
    private val _selectedPainting = MutableLiveData<Painting?>()
    val selectedPainting: LiveData<Painting?> = _selectedPainting

    init {
        fetchPaintings()
    }

    private fun fetchPaintings() {
        viewModelScope.launch {
            try {
                _paintings.value = RetrofitClient.apiService.getPaintings()
            } catch (e: Exception) {
                // Maneja el error
                Log.e("viewmodel","fallo en la api de pinturas")
            }
        }
    }

    fun fetchPaintingDetails(id: Int) {
        viewModelScope.launch {
            try {
                _selectedPainting.value = RetrofitClient.apiService.getPaintingDetails(id)
            } catch (e: Exception) {
                Log.e("viewmodel", "fallo en la api de detalle de pintura", e)
            }
        }
    }
}