package com.example.danp_artgallery.model

import com.example.danp_artgallery.R

object DataProvider {
    val expositionList = listOf(
        Exposition(
            imageResource = listOf(R.drawable.carpintero_nidos, R.drawable.ccunsa, R.drawable.ccunsalocal ) ,
            title = "CARPINTERO DE NIDOS",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = listOf(R.drawable.carpintero_nidos, R.drawable.ccunsa, R.drawable.ccunsalocal ) ,
            title = "EXPOSITION1",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = listOf(R.drawable.carpintero_nidos, R.drawable.ccunsa, R.drawable.ccunsalocal ) ,
            title = "EXPOSITION2",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        Exposition(
            imageResource = listOf(R.drawable.carpintero_nidos, R.drawable.ccunsa, R.drawable.ccunsalocal ) ,
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
