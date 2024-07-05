package com.example.danp_artgallery.model

import androidx.annotation.DrawableRes
import com.example.danp_artgallery.R
import java.io.Serializable

data class Picture(
    @DrawableRes val imageResource:Int,
    val title: String,
    val description: String,
    val author: String,
    val tecnique: String,
): Serializable

class PictureRepository {
    private val pictures = listOf(
        Picture( imageResource = R.drawable.ccunsa,
            title = "CARPINTERO DE NIDOS",
            description = "Esta es una prueba de desc...",
            author = "Author 1",
            tecnique = "Tecnica 1"
        ),
        Picture(
            imageResource = R.drawable.ccunsa,
            title = "Arbol",
            description = "picture 2",
            author = "asdas",
            tecnique = "Tecnica 1"
        ),
        Picture(
            imageResource = R.drawable.ccunsa,
            title = "Provenir",
            description = "picture 3",
            author = "Author 1",
            tecnique = "aksdj"
        ),
    )
    fun getPictures(): List<Picture> {
        return pictures
    }

    fun getPictureByTitle(title: String): Picture? {
        return PictureDataProvider.pictureList.find { it.title == title }
    }
}