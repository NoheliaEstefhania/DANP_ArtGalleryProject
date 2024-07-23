package com.example.danp_artgallery.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class PaintAttributes (
    @DrawableRes val imageResource:Int,
    val title: String,
    val descriptionTitle : String,
    val description : String,
    val authorTitle: String,
    val author: String,
    val tecniqueTitle: String,
    val tecnique: String
): Serializable