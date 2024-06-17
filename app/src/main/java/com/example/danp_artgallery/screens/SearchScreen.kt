package com.example.danp_artgallery.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import com.example.danp_artgallery.R
import androidx.compose.ui.unit.dp


@Composable
fun SearchScreen(){
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp), // Ajusta el padding según sea necesario
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // Reemplaza 'logo' con el ID de tu imagen
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),  // Esto agrega padding para evitar que el contenido se solape con la topBar.
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_app), // Reemplaza 'your_image_resource' con el ID de tu imagen
                    contentDescription = "Search Button",
                    modifier = Modifier
                        .clickable {
                            // Acción cuando se hace clic en la imagen (por ejemplo, navegación a otra pantalla)
                        }
                )
            }
        }
    )
}