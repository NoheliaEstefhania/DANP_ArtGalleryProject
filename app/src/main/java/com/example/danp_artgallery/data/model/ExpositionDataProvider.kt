package com.example.danp_artgallery.data.model

import com.example.danp_artgallery.R

object ExpositionDataProvider {
    val expositionList = listOf(
        ExpositionAttributes(
            imageResource = listOf(R.drawable.carousel01, R.drawable.carousel02, R.drawable.carousel03 ) ,
            title = "CARPINTERO DE NIDOS",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        ExpositionAttributes(
            imageResource = listOf(R.drawable.carousel02, R.drawable.carousel03, R.drawable.carousel04 ) ,
            title = "EXPOSITION1",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        ExpositionAttributes(
            imageResource = listOf(R.drawable.carousel03, R.drawable.carousel04, R.drawable.carousel01 ) ,
            title = "EXPOSITION2",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        ),
        ExpositionAttributes(
            imageResource = listOf(R.drawable.carousel04, R.drawable.carousel01, R.drawable.carousel02 ) ,
            title = "EXPOSITION3",
            expositions = listOf(
                "De los hijos que se van. Daniel Gallegos. Daniel Gallegos. " +
                        "Centro Cultural UNSA. Galería I.Centro Cultural UNSA.",
                "Daniel Gallegos"
            )
        )
    )

    fun getExpositionByTitle(title: String): ExpositionAttributes? {
        return expositionList.find { it.title == title }
    }
}
