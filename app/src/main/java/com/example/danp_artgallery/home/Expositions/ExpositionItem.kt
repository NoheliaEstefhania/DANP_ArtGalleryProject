package com.example.danp_artgallery.home.Expositions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.data.model.Exposition

@Composable
fun ExpositionItem(exposition: Exposition) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exposition.name)
            Text(text = "Artista: ${exposition.artist}")
            //Text(text = exposition.description)
            Text(text = "Ubicación: ${exposition.location}")
            Text(text = "Última publicación: ${exposition.last_date_publication}")
        }
    }

}