package com.example.danp_artgallery.screens.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import com.example.danp_artgallery.model.DataProvider.expositionList
import com.example.danp_artgallery.model.Exposition
import com.example.danp_artgallery.screens.views.ImageCarousel

@Composable
fun ExpositionsDetailFunction(
    exposition: Exposition,
    navigateToExpositionDetail: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { navigateToExpositionDetail(exposition.title) }
        ) {
            val imageModifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
            MaterialTheme {
                Surface {
                    ImageCarousel(images = exposition.imageResource)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = exposition.title, style = typography.titleSmall)

            for (item in exposition.expositions) {
                Text(text = item, style = typography.bodySmall)
            }
        }
    }
}

@Composable
fun ExpositionList(
    expositionList: List<Exposition>,
    navigateToExpositionDetail: (String) -> Unit
) {
    LazyColumn {
        items(expositionList) { item ->
            ExpositionsDetailFunction(
                exposition = item,
                navigateToExpositionDetail = navigateToExpositionDetail
            )
        }
    }
}
