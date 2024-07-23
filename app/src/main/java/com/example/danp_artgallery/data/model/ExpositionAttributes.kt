package com.example.danp_artgallery.data.model

import androidx.annotation.DrawableRes
import java.io.Serializable


data class ExpositionAttributes(
    @DrawableRes val imageResource:List<Int>,
    val title: String,
    val expositions: List<String>
): Serializable
