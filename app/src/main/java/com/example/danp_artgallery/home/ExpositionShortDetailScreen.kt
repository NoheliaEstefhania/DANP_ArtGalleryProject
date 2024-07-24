package com.example.danp_artgallery.home

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
import com.example.danp_artgallery.data.model.ExpositionAttributes

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
                        contentPadding = PaddingValues(horizontal = 4.dp),

                        )
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
    expositionList: List<ExpositionAttributes>,
    navigateToExpositionDetail: (String) -> Unit
) {
    LazyColumn {
        items(expositionList) { item ->
            ExpositionShortDetailScreen(
                exposition = item,
                navigateToExpositionDetail = navigateToExpositionDetail
            )
        }
    }
}
