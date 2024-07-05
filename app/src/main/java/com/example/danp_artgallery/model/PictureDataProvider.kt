package com.example.danp_artgallery.model

import com.example.danp_artgallery.R

object PictureDataProvider {
    val pictureList = listOf(
        Picture(
            imageResource = R.drawable.ccunsa,
            title = "CARPINTERO DE NIDOS",
            description = "picture 1",
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

    fun getPictureByTitle(title: String): Picture? {
        return pictureList.find { it.title == title }
    }
}
