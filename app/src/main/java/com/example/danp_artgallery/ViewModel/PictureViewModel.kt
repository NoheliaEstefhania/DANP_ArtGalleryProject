package com.example.danp_artgallery.ViewModel

import androidx.lifecycle.ViewModel
import com.example.danp_artgallery.model.DataProvider
import com.example.danp_artgallery.model.Picture
import com.example.danp_artgallery.model.PictureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PictureViewModel : ViewModel() {
    private val pictureRepository = PictureRepository()

    private val _pictures = MutableStateFlow<List<Picture>>(emptyList())
    val pictures: StateFlow<List<Picture>> = _pictures

    init {
        fetchPictures()
    }

    private fun fetchPictures() {
        _pictures.value = pictureRepository.getPictures()
    }
}
