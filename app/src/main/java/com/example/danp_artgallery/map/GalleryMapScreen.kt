package com.example.danp_artgallery.map

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R
import com.example.danp_artgallery.map.collection.collectRoomDataFromJSON
import com.example.danp_artgallery.map.collection.parseRoomsFromJSON
import com.example.danp_artgallery.map.procedures.DrawRooms


private const val title = "GALLERY MAP"
@Composable
fun GalleryMapScreen(context: Context?){
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // Ajusta el padding según sea necesario
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow), // Reemplaza 'arrow' con el ID de tu imagen de flecha
                        contentDescription = "flecha",
                        modifier = Modifier
                            .padding(start = 16.dp) // Añade padding al lado izquierdo
                            .size(50.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar el logo al centro

                    Image(
                        painter = painterResource(id = R.drawable.logo), // Reemplaza 'logo' con el ID de tu imagen
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(50.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para empujar el ícono de configuración a la derecha

                    Image(
                        painter = painterResource(id = R.drawable.settings_img), // Reemplaza 'settings_img' con el ID de tu ícono de configuración
                        contentDescription = "configuración",
                        modifier = Modifier
                            .padding(end = 16.dp) // Añade padding al lado derecho
                            .size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la fila de íconos y el texto, ajusta el valor según sea necesario

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
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
                if (context != null){
                    // Loading rooms data
                    val jsonString = collectRoomDataFromJSON(context, "GalleryRoomsData.json")
                    jsonString?.let {
                        // Parsing room data
                        val rooms = parseRoomsFromJSON(it).rooms
                        DrawRooms(rooms = rooms)
                    } ?: run {
                        DrawRooms(rooms = emptyList())
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun GalleryMapPreview(){
    GalleryMapScreen(null)
}