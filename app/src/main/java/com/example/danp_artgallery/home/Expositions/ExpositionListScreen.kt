package com.example.danp_artgallery.home.Expositions

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.data.model.Painting
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel
import com.example.danp_artgallery.navigation.Screens
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme


@Composable
fun ExpositionListScreen(
    viewModel: ExpositionViewModel = viewModel(),
    navigateToExpositionDetail: (Int) -> Unit,
    navController: NavController
) {
    val expositions by viewModel.expositions.observeAsState(emptyList())

    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(expositions) { exposition ->
            val images = remember { mutableStateOf<List<Painting>>(emptyList()) }

            LaunchedEffect(exposition.id) {
                viewModel.fetchPictures(exposition.id)
            }
            val fetchedPictures by viewModel.selectedExpositionPictures.observeAsState(emptyMap())


            images.value = (fetchedPictures[exposition.id] ?: emptyList()) as List<Painting>
            DANP_ArtGalleryTheme {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { navigateToExpositionDetail(exposition.id) }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {

                        val imageUrls = images.value.map { it.image_min ?: "no image" }
                        Log.d("ExpositionListScreen", "Exposition ID: ${exposition.id}, Images: ${imageUrls.size}")

                        if (imageUrls.isNotEmpty()) {
                            ImageCarousel(
                                imageUrls = imageUrls,
                                navController = rememberNavController(),
                                onImageClick = { imageUrl ->
                                    val paintingId = images.value.find { it.image_min == imageUrl }?.id
                                    paintingId?.let {
                                        navController.navigate("${Screens.PaintingDetailScreen.name}/$it")
                                    }
                                }
                            )
                        } else {
                            Text(text = "No images available")
                        }


                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = exposition.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = exposition.description,
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Justify
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Location: ${exposition.location}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}