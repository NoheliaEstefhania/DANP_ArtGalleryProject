package com.example.danp_artgallery.data.model

import com.example.danp_artgallery.R

object ArtGalleryInformationDataProvider {
    val information = ArtGalleryInformationAttributes(
        R.drawable.ccunsalocal,
        title = "CENTRO CULTURAL DE LA UNSA\n",
        detail = "Lorem ipsum dolor sit amet consectetur adipiscing elit morbi ut " +
                "hac in primis feugiat penatibus consequat, aenean malesuada leo mattis " +
                "molestie justo cursus iaculis nisl eget fusce nascetur nunc.\n",
        direction = "Ubication: Santa Catalina 101, Arequipa 04001\n",
        listOf(
            "Availability:\n",
            "Monday to Friday: 8 a.m. – 8:15 p.m.\n",
            "Saturday: 9:30 a.m. – 4:15 p.m.\n",
            "Sunday: Closed\n"
        )
    )
}