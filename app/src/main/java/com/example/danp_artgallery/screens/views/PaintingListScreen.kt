package com.example.danp_artgallery.screens.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danp_artgallery.data.viewmodel.PaintingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PaintingListScreen(
    viewModel: PaintingViewModel = viewModel(),
    onPaintingClick: (Int) -> Unit
) {
    val paintings by viewModel.paintings

    Scaffold {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(paintings) { painting ->
                Log.d("PaintingListScreen", "Painting: ${painting.title}, ID: ${painting.id}")
                PaintingItem(painting = painting, onClick = {
                    Log.d("PaintingListScreen", "Clicked on painting ID: ${painting.id}")
                    onPaintingClick(painting.id)
                })
            }
        }
    }
}