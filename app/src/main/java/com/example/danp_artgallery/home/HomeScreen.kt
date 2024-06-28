package com.example.danp_artgallery.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R
import com.example.danp_artgallery.model.DataProvider
import com.example.danp_artgallery.screens.section.ExpositionList

@Composable
fun HomeScreen(navigateToExpositionDetail: (String) -> Unit) {
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
                    val expositions = DataProvider.expositionList
                    ExpositionList(
                        expositionList = expositions,
                        navigateToExpositionDetail = navigateToExpositionDetail
                    )
                }
            }
        }
    )
}
