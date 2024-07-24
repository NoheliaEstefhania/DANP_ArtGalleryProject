import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.data.model.ExpositionDataProvider
import com.example.danp_artgallery.home.ImageCarousel
import com.example.danp_artgallery.navigation.CustomTopBar

@Composable
fun ExpositionLargeDetailScreen(expositionTitle: String, navController: NavController) {
    val exposition = ExpositionDataProvider.getExpositionByTitle(expositionTitle)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                CustomTopBar(navController = navController)

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = expositionTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

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
                exposition?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        MaterialTheme {
                            Surface {
                                ImageCarousel(images = exposition.imageResource,
                                    contentPadding = PaddingValues(horizontal = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        it.expositions.forEach { detail ->
                            Text(text = detail,
                            modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }
                    }
                } ?: run {
                    Text(text = "Exposition not found")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewExpositionDetailScreen() {
    MaterialTheme {
        Surface {
            ExpositionLargeDetailScreen(expositionTitle = "CARPINTERO DE NIDOS", navController = rememberNavController())
        }
    }
}
