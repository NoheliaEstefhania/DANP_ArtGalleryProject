import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.home.Expositions.ImageCarousel
import com.example.danp_artgallery.navigation.CustomTopBar
import com.example.danp_artgallery.navigation.Screens

@Composable
fun ExpositionLargeDetailScreen(
    viewModel: ExpositionViewModel,
    expositionId: Int,
    navController: NavController
) {

    LaunchedEffect(Unit) {
        Log.d("ExpositionLargeDetailScreen", "Fetching details for exposition ID: $expositionId")
        viewModel.fetchExpositionDetails(expositionId)
        viewModel.fetchPictures(expositionId)
    }
    val expositionDetails by viewModel.selectedExposition.observeAsState()
    val fetchedPictures by viewModel.selectedExpositionPictures.observeAsState(emptyMap())
    val images = fetchedPictures[expositionId] ?: emptyList()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                CustomTopBar(navController = navController)

                Spacer(modifier = Modifier.height(16.dp))
                expositionDetails?.let {
                    Text(
                        text = it.name ?: "No name available",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        },
        content = { paddingValues ->
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        expositionDetails?.let {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (images.isNotEmpty()) {
                                    ImageCarousel(
                                        imageUrls = images.map { it!!.image_min },
                                        navController = navController,
                                        onImageClick = { imageUrl ->
                                            val paintingId = images.find { it!!.image_min == imageUrl }?.id
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
                                    text = it.description,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier.padding(
                                        horizontal = 24.dp
                                    )
                                )
                            }
                        } ?: run {
                            Text(text = "Exposition not found")
                        }
                    }
                }
            }
        }
    )
}