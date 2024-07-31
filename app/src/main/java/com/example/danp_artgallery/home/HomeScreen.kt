package com.example.danp_artgallery.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.danp_artgallery.data.model.ExpositionDataProvider
import com.example.danp_artgallery.data.viewmodel.ExpositionViewModel
import com.example.danp_artgallery.home.Expositions.ExpositionList
import com.example.danp_artgallery.home.Expositions.ExpositionListScreen
import com.example.danp_artgallery.navigation.CustomTopBarHome

@Composable
fun HomeScreen(navigateToExpositionDetail: (Int) -> Unit,navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBarHome(navController)
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
                        .padding(16.dp),

                ) {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "CURRENT EXPOSITIONS",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                            ),

                        )
                    }

                    // Using the ExpositionViewModel to get the list of expositions
                    val viewModel: ExpositionViewModel = viewModel()
                    ExpositionListScreen(
                        viewModel = viewModel,
                        navigateToExpositionDetail = { id -> navigateToExpositionDetail(id) },
                        navController = navController
                    )
                }
            }
        }
    )
}
