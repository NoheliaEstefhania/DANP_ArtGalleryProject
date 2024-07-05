package com.example.danp_artgallery.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.danp_artgallery.R
import com.example.danp_artgallery.ViewModel.PictureViewModel
import com.example.danp_artgallery.screens.section.PictureList

@Composable
fun HomeScreen(navigateToExpositionDetail: (String) -> Unit, pictureViewModel: PictureViewModel = viewModel()) {
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
                    modifier = Modifier.size(50.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val pictures by pictureViewModel.pictures.collectAsStateWithLifecycle()
                    PictureList(
                        pictureList = pictures,
                        navigateToPictureDetail = navigateToExpositionDetail
                    )
                }
            }
        }
    )
}
