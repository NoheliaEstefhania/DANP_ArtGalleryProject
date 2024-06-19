package com.example.danp_artgallery.model

import com.example.danp_artgallery.R

object DataProvider {

    val exposition = Exposition(
        imageResource = R.drawable.ccunsa,
        title = "CARPINTERO DE NIDOS\n",
        expositions = listOf(
            "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n",
            "Daniel Gallegos\n"
        )
    )

    val expositionList = listOf(
        exposition,
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "CARPINTERO DE NIDOS",
            expositions = listOf(
                "\nDe los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n",
                "Daniel Gallegos\n"
            )
        ),
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "EXPOSITION2\n",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n",
                "Daniel Gallegos\n"
            )
        ),
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "EXPOSITION3\n",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n",
                "Daniel Gallegos\n"
            )
        )
    )
    fun getExpositionByTitle(title: String): Exposition? {
        return expositionList.find { it.title == title }
    }
}