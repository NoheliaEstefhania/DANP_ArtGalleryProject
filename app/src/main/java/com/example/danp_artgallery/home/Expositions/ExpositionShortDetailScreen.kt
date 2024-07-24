package com.example.danp_artgallery.home.Expositions

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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.data.model.ExpositionAttributes
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

@Composable
fun ExpositionShortDetailScreen(
    exposition: ExpositionAttributes,
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
            MaterialTheme {
                Surface {
                    ImageCarousel(
                        images = exposition.imageResource,
                        navController = rememberNavController()
                        )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = exposition.title,
                style = typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            for (item in exposition.expositions) {
                Text(text = item, style = typography.bodySmall)
            }
        }
    }
}
@Composable
fun ExpositionList(
    expositionList: List<ExpositionAttributes>,
    navigateToExpositionDetail: (String) -> Unit
) {
    LazyColumn {
        items(expositionList) { item ->
            DANP_ArtGalleryTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ExpositionShortDetailScreen(
                        exposition = item,
                        navigateToExpositionDetail = navigateToExpositionDetail
                    )
                }
            }
        }
    }
}
