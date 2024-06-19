package com.example.danp_artgallery.beacon

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.R
import com.example.danp_artgallery.beacon.utils.BeaconData
import com.example.danp_artgallery.beacon.utils.BeaconViewModel
import com.example.danp_artgallery.beacon.PermissionsHelper
import com.example.danp_artgallery.beacon.allPermissionGroupsGranted
import com.example.danp_artgallery.beacon.utils.BeaconSearcher
import org.altbeacon.beacon.Beacon


private const val title = "BEACON"


@Composable
fun BeaconItem(beacon: BeaconData) {
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
fun BeaconList(beacons: List<Beacon>) {
    LazyColumn {
        items(beacons) { beacon ->
            BeaconItem(BeaconData.fromAltBeacon(beacon))
        }
    }
}

@Composable
fun BeaconScreen(beaconViewModel: BeaconViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val permissionsHelper = PermissionsHelper(context)
    val permissionGroups by remember { mutableStateOf(permissionsHelper.beaconScanPermissionGroupsNeeded()) }
    val beacons by beaconViewModel.beacons.observeAsState(emptyList())
    val isMonitoring by beaconViewModel.isMonitoring.observeAsState(false)
    val isRanging by beaconViewModel.isRanging.observeAsState(false)

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp) // Ajusta el padding según sea necesario
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
                    style = typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        },
        content = { paddingValues ->
            if (allPermissionGroupsGranted(context, permissionGroups)) {
                LaunchedEffect(Unit) {
                    context.startService(Intent(context, BeaconSearcher::class.java))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp) // Puedes ajustar el padding aquí si prefieres que sea menor
                        .padding(top = paddingValues.calculateTopPadding()),  // Mantiene solo el padding superior necesario para evitar solapamiento                contentAlignment = Alignment.Center
                ) {
                    Column {
                        BeaconList(beacons = beacons)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (isMonitoring) {
                                beaconViewModel.stopMonitoring()
                            } else {
                                beaconViewModel.startMonitoring()
                            }
                        }
                    ) {
                        Text(if (isMonitoring) "Stop Monitoring" else "Start Monitoring")
                    }
                    Button(
                        onClick = {
                            if (isRanging) {
                                beaconViewModel.stopRangingBeacons()
                            } else {
                                beaconViewModel.startRangingBeacons()
                            }
                        }
                    ) {
                        Text(if (isRanging) "Stop Monitoring" else "Start Monitoring")
                    }
                }
            } else {
                BeaconScanPermissionsScreen(navController = navController)
            }
        }
    )
}
