package com.example.danp_artgallery.data.model

import androidx.annotation.DrawableRes

data class ArtGalleryInformationAttributes(
    @DrawableRes val imageResource:Int,
    val title: String,
    val detail: String,
    val direction: String,
    val information: List<String>
)