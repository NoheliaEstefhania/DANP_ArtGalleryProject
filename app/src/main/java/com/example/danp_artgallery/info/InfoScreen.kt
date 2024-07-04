package com.example.danp_artgallery.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.R
import com.example.danp_artgallery.navigation.CustomTopBar
import com.example.danp_artgallery.screens.section.InformationDetailView
import com.example.danp_artgallery.screens.section.information


@Composable
fun InfoScreen(navController: NavController){
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Ajusta el padding según sea necesario
            ) {

                CustomTopBar(navController = navController)

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la fila de íconos y el texto, ajusta el valor según sea necesario
                Text(
                    text = "Information",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la fila de íconos y el texto, ajusta el valor según sea necesario

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
                    InformationDetailView(artGallery = information)
                }
            }
        }
    )
}

@Preview
@Composable
fun InfoPreview(){
    InfoScreen(navController = rememberNavController())
}