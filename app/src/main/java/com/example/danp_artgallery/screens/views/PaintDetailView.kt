package com.example.danp_artgallery.screens.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel
/*
@Composable
fun PaintDetailView(paintingId: Int, paintingViewModel: PaintingViewModel = viewModel()) {
    val painting = paintingViewModel.paintings.value.find { it.id == paintingId }
    painting?.let {
        LazyColumn {
            item {
                PaintingItem(paint = it)
            }
        }
    }
}
*/