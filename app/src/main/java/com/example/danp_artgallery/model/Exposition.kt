package com.example.danp_artgallery.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Exposition(
    @DrawableRes val imageResource:List<Int>,
    val title: String,
    val expositions: List<String>
): Serializable
