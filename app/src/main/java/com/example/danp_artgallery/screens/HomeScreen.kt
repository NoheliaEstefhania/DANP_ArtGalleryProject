package com.example.danp_artgallery.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danp_artgallery.R


@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            Image(
                painter = painterResource(id = R.drawable.logo), // Reemplaza 'your_image_resource' con el ID de tu imagen
                contentDescription = "logo",
                modifier = Modifier
                    .size(50.dp)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp) // Añade padding horizontal a la LazyColumn
                ) {
                    items(3) { index ->
                        Text(
                            text = "Item $index",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(vertical = 8.dp) // Añade padding vertical a cada item
                        )
                    }
                }
            }
        }
    )
}

