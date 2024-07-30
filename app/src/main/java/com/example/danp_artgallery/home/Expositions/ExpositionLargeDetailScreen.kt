import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.home.Expositions.ImageCarousel
import com.example.danp_artgallery.navigation.CustomTopBar

@Composable
fun ExpositionLargeDetailScreen(viewModel: ExpositionViewModel , expositionId: Int, navController: NavController) {

    LaunchedEffect(Unit) {
        Log.d("ExpositionLargeDetailScreen", "Fetching details for exposition ID: $expositionId")
        viewModel.fetchExpositionDetails(expositionId)
    }
    val expositionDetails by viewModel.selectedExposition.observeAsState()

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                expositionDetails?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        /*MaterialTheme {
                            Surface {
                                ImageCarousel(images = exposition.imageResource,
                                    navController = navController
                                )
                            }
                        }*/

                        Spacer(modifier = Modifier.height(16.dp))

                        //it.expositionDetails.forEach { detail ->
                        Text(
                            text = it.description,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                    }
                } ?: run {
                    Text(text = "Exposition not found")
                }
            }
        }
    )
}
/*
@Preview(showBackground = true)
@Composable
fun PreviewExpositionDetailScreen() {
    MaterialTheme {
        Surface {
            //ExpositionLargeDetailScreen(expositionTitle = "CARPINTERO DE NIDOS", navController = rememberNavController())
            ExpositionLargeDetailScreen(expositionId = 2, navController = rememberNavController())

        }
    }
}
*/
  /*
    Log.d("ExpositionLargeDetailScreen", "Fetching details for exposition ID: $expositionId")
    LaunchedEffect(expositionId) {
        viewModel.fetchExpositionDetails(expositionId)
    }

    val exposition by viewModel.selectedExposition.observeAsState()

    exposition?.let {
        Column(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.name)
            //Text(text = "Author: ${it.author}")
            Text(text = "Description: ${it.description}")
            Text(text = "Technique: ${it.artist}")
            Text(text = "Location: ${it.location}")
            //Text(text = "Space: ${it.space}")
        }
    }/
}*/