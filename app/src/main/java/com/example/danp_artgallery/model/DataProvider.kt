package com.example.danp_artgallery.model

import com.example.danp_artgallery.R

object DataProvider {
    val expositionList = listOf(
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "CARPINTERO DE NIDOS",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "EXPOSITION1",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "EXPOSITION2",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = R.drawable.ccunsa,
            title = "EXPOSITION3",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        )
    )

    fun getExpositionByTitle(title: String): Exposition? {
        return expositionList.find { it.title == title }
    }
}
