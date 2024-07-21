package com.example.danp_artgallery.screens.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R
import com.example.danp_artgallery.model.DataProvider

@Composable
fun ExpositionDetailScreen(expositionTitle: String) {
    val exposition = DataProvider.getExpositionByTitle(expositionTitle)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                exposition?.let {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
 /*                       it.expositions.forEach{ imageResource ->
                            Image(
                            painter = painterResource(id = imageResource),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        )
                        }*/
                        MaterialTheme {
                            Surface {
                                ImageCarousel(images = exposition.imageResource)
                            }
                        }
                        Text(text = it.title)
                        Spacer(modifier = Modifier.height(16.dp))
                        it.expositions.forEach { detail ->
                            Text(text = detail)
                        }
                    }
                } ?: run {
                    Text(text = "Exposition not found")
                }
            }
        }
    )
}
