package com.example.danp_artgallery.screens.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.data.model.PaintAttributes
import com.example.danp_artgallery.data.model.PaintDataProvider
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

@Composable
fun PaintsDetailFunction(paint: PaintAttributes) {
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = paint.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Image(
                painter = painterResource(id = paint.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Text(
                text = paint.descriptionTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = paint.description,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = paint.authorTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = paint.author,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = paint.tecniqueTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = paint.tecnique,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun PaintDetailView(paint: PaintAttributes) {
    LazyColumn {
        item {
            PaintsDetailFunction(paint = paint)
        }
    }
}

@Preview
@Composable
fun PaintPreview(){
    DANP_ArtGalleryTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PaintsDetailFunction(PaintDataProvider.paint)
        }
    }

}