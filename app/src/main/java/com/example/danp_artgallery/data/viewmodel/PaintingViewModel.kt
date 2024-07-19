package com.example.danp_artgallery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.danp_artgallery.data.model.Painting
import com.example.danp_artgallery.data.network.RetrofitClient
import kotlinx.coroutines.launch

class PaintingViewModel : ViewModel() {
    private val _paintings = mutableStateOf<List<Painting>>(emptyList())
    val paintings = _paintings

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
}