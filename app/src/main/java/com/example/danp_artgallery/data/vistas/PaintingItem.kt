package com.example.danp_artgallery.data.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.danp_artgallery.data.model.Painting

@Composable
fun PaintingItem(painting: Painting) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        //elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(painting.image_min),
                contentDescription = painting.title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = painting.title)
            Text(text = painting.author)
            Text(text = painting.description)
        }
    }
}