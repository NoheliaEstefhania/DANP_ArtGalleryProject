package com.example.danp_artgallery.beacon


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R
import com.example.danp_artgallery.screens.section.Exposition
import com.example.danp_artgallery.screens.section.ExpositionsDetailFunction

data class Beacon(
    val userId: String,
    val otroDato: String
)
val beaconList = listOf(
    Beacon(userId = "example of id kjfjdhbfjhdbfhdbfhbsdkhjbkhjfb\n", otroDato = "name\n"),
        Beacon(userId = "example of id kjfjdhbfjhdbfhdbfhbsdkhjbkhjfb\n", otroDato = "name\n")
)

@Composable
fun BeaconsDetailFunction(beacon: Beacon) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
            Text(text = beacon.userId, style = typography.bodySmall)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = beacon.otroDato, style = typography.bodySmall)

        }
    }
}

@Composable
fun BeaconList(beaconList: List<Beacon>) {
    LazyColumn {
        items(beaconList) { item ->
            BeaconsDetailFunction(beacon = item)
        }
    }
}

@Preview
@Composable
fun BeaconDetailPreview(){
    BeaconsDetailFunction(beaconList[0])
}