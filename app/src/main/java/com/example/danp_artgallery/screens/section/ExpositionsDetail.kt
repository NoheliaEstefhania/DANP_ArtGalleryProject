package com.example.danp_artgallery.screens.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.model.Exposition
import com.example.danp_artgallery.model.Picture

@Composable
fun PicturesDetailFunction(
    picture: Picture,
    navigateToPictureDetail: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { navigateToPictureDetail(picture.title) }
        ) {
            val imageModifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))

            Image(
                painter = painterResource(id = picture.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = picture.title, style = typography.titleSmall)

            Text(text = picture.description, style = typography.titleSmall)

            Text(text = picture.author, style = typography.titleSmall)
        }
    }
}

@Composable
fun PictureList(
    pictureList: List<Picture>,
    navigateToPictureDetail: (String) -> Unit
) {
    LazyColumn {
        items(pictureList) { item ->
            PicturesDetailFunction(
                picture = item,
                navigateToPictureDetail = navigateToPictureDetail
            )
        }
    }
}
