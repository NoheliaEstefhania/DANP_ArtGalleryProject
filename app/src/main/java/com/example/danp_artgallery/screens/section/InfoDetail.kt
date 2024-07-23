package com.example.danp_artgallery.screens.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.data.model.ArtGalleryInformationAttributes
import com.example.danp_artgallery.data.model.ArtGalleryInformationDataProvider

@Composable
fun InfoDetailFunction(information: ArtGalleryInformationAttributes) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))

            Image(
                painter = painterResource(id = information.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = information.title, style = typography.titleSmall)
            Text(text= information.detail, style = typography.titleSmall)
            Text(text=information.direction, style = typography.titleSmall)

            for (item in information.information) {
                Text(text = item, style = typography.bodySmall)
            }
        }
    }
}

@Composable
fun InformationDetailView(artGallery: ArtGalleryInformationAttributes) {
    LazyColumn {
        item {
            InfoDetailFunction(information = artGallery)
        }
    }
}

@Preview
@Composable
fun InfoPreview() {
    InfoDetailFunction(ArtGalleryInformationDataProvider.information)
}