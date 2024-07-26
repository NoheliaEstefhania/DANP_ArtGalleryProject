package com.example.danp_artgallery.data.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class PaintAttributes (
    val audio: String,
    val author: String,
    val description: String,
    val id: Int,
    val image: String,
    val image_min: String,
    val location: String,
    val space: String,
    val technique: String,
    val title: String
): Serializable