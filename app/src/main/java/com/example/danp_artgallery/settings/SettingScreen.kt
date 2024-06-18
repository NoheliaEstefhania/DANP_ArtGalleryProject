package com.example.danp_artgallery.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R


private val title = "SELECT THE ART GALLERY"
@Composable
fun SettingScreen(){
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
                    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
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
                SeccionInterruptores()
            }
        }
    )
}

@Composable
fun SeccionInterruptores() {
    // Estados para los interruptores
    val estadoNotificacion = remember { mutableStateOf(false) }
    val estadoUbicacion = remember { mutableStateOf(false) }
    val estadoStreamingAudio = remember { mutableStateOf(false) }
    val idiomaSeleccionado = remember { mutableStateOf("Spanish") }

    Column {
        CardInterruptor(label = "Notification", estado = estadoNotificacion)
        CardInterruptor(label = "Location", estado = estadoUbicacion)
        CardInterruptor(label = "Streaming audio", estado = estadoStreamingAudio)
        CardSelectionLanguaje(idiomaSeleccionado)
    }
}

@Composable
fun CardInterruptor(label: String, estado: MutableState<Boolean>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), // Ajusta el padding según sea necesario
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label)
            Spacer(modifier = Modifier.weight(1f))
            Switch(checked = estado.value, onCheckedChange = { estado.value = it })
        }
    }
}

@Composable
fun CardSelectionLanguaje(idiomaSeleccionado: MutableState<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            //.background(Color.Blue),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        //backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                //.background(Color.Blue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Language", modifier = Modifier.padding(end = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
            LangujeOpcion("English", idiomaSeleccionado)
            LangujeOpcion("Spanish", idiomaSeleccionado)
        }
    }
}

@Composable
fun LangujeOpcion(idioma: String, languajeSelected: MutableState<String>) {
    Text(
        text = idioma,
        color = if (languajeSelected.value == idioma) Color.Blue else Color.Gray,
        modifier = Modifier
            .clickable { languajeSelected.value = idioma }
            .padding(8.dp)
    )
}

@Preview
@Composable
fun SettingScreenPreview(){
    SettingScreen()
}