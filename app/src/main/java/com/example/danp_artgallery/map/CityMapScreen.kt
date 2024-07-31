package com.example.danp_artgallery.map

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.map.collection.collectRoomDataFromJSON
import com.example.danp_artgallery.map.collection.parseRoomsFromJSON
import com.example.danp_artgallery.map.procedures.DrawRooms
import com.example.danp_artgallery.map.procedures.ShowMap
import com.example.danp_artgallery.navigation.CustomTopBar


private const val title = "SELECT THE ART GALLERY"
@Composable
fun CityMapScreen(context: Context?,navController: NavController){

    var showMap by remember { mutableStateOf(true) }

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
                    text = title,
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
                    .padding(paddingValues),  // Esto agrega padding para evitar que el contenido se solape con la topBar.
                contentAlignment = Alignment.Center
            ) {
                if (showMap) {
                    ShowMap(onMarkerClick = { showMap = false })
                } else {
                    if (context != null){
                        // Loading rooms data
                        val jsonString = collectRoomDataFromJSON(
                            context,
                            "GalleryRoomsData.json"
                        )
                        jsonString?.let {
                            // Parsing room data
                            val rooms = parseRoomsFromJSON(it).rooms
                            DrawRooms(rooms = rooms,context = context )
                        } ?: run {
                            DrawRooms(rooms = emptyList(), context = context)
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun CityMapPreview(){
    CityMapScreen(null, navController = rememberNavController())
}