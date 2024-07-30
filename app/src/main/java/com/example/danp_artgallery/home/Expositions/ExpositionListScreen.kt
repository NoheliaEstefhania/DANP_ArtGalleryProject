package com.example.danp_artgallery.home.Expositions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel


@Composable
fun ExpositionListScreen(
    viewModel: ExpositionViewModel = viewModel()
) {
    val expositions by viewModel.expositions.observeAsState(emptyList())

    Scaffold() { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expositions) { exposition ->
                ExpositionItem(exposition)
            }
        }
    }
}