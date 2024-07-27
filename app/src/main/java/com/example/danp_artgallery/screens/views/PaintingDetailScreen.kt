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


@Composable
fun PaintingDetailScreen(viewModel: PaintingViewModel, paintingId: Int) {
    LaunchedEffect(paintingId) {
        viewModel.fetchPaintingDetails(paintingId)
    }

    val painting by viewModel.selectedPainting.observeAsState()

    painting?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(it.image),
                contentDescription = it.title,
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.title)
            Text(text = "Author: ${it.author}")
            Text(text = "Description: ${it.description}")
            Text(text = "Technique: ${it.technique}")
            Text(text = "Location: ${it.location}")
            Text(text = "Space: ${it.space}")
        }
    }
}