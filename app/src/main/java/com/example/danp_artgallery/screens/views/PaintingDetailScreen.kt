package com.example.danp_artgallery.screens.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import coil.compose.rememberImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.danp_artgallery.navigation.CustomTopBar

@Composable
fun PaintingDetailScreen(navController: NavController, viewModel: PaintingViewModel, paintingId: Int) {
    LaunchedEffect(paintingId) {
        viewModel.fetchPaintingDetails(paintingId)
    }

    val painting by viewModel.selectedPainting.observeAsState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                CustomTopBar(navController = navController) // Replace with your TopAppBar
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "PAINT DETAILS", // Adjust the title as needed
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
                contentAlignment = Alignment.Center
            ) {
                painting?.let {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberImagePainter(it.image),
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        AudioPlayerButton(it.audio)
                        Text(text = it.title)
                        //Text(text = "Author: ${it.author}")
                        Text(text = "Description: ${it.description}")
                        Text(text = "Technique: ${it.technique}")
                        Text(text = "Location: ${it.location}")
                        Text(text = "Space: ${it.space}")
                    }
                }
            }
        }
    )
}