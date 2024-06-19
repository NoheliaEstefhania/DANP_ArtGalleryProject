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
import com.example.danp_artgallery.beacon.utils.Beacon
import com.example.danp_artgallery.screens.section.Exposition
import com.example.danp_artgallery.screens.section.ExpositionsDetailFunction

@Composable
fun BeaconItem(beacon: Beacon) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "ID1: ${beacon.id1}", style = typography.bodySmall)
            Text(text = "ID2: ${beacon.id2}", style = typography.bodySmall)
            Text(text = "ID3: ${beacon.id3}", style = typography.bodySmall)
            Text(text = "RSSI: ${beacon.rssi}", style = typography.bodySmall)
            Text(text = "Distance: ${beacon.distance} meters", style = typography.bodySmall)
        }
    }
}

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
fun BeaconList(beacons: List<Beacon>) {
    LazyColumn {
        items(beacons) { beacon ->
            BeaconItem(beacon)
        }
    }
}

@Preview
@Composable
fun BeaconDetailPreview(){
    BeaconsDetailFunction(beaconList[0])
}