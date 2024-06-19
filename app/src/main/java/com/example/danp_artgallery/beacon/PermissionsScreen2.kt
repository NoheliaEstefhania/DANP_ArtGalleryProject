package com.example.danp_artgallery.beacon

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.danp_artgallery.beacon.utils.PermissionsHelper
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

private const val title = "PERMISOS"

@Composable
fun PermissionScreen(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionsHelper = PermissionsHelper(context)
    val permissionGroups = permissionsHelper.beaconScanPermissionGroupsNeeded(backgroundAccessRequested = true)

    val permissionStates = remember { mutableStateListOf(*Array(permissionGroups.size) { false }) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.forEach { (permission, isGranted) ->
            Log.d("PermissionScreen", "$permission granted: $isGranted")
        }
        permissionGroups.forEachIndexed { index, group ->
            permissionStates[index] = group.all { permissions[it] == true }
        }
    }
    LaunchedEffect(Unit) {
        permissionGroups.forEachIndexed { index, group ->
            permissionStates[index] =
                group.all { permissionsHelper.isPermissionGranted(it) }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Para utilizar la aplicación, necesitamos tu permiso para acceder a la ubicación.",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            permissionGroups.forEachIndexed { index, group ->
                Button(
                    onClick = { requestPermissionLauncher.launch(group) },
                    modifier = Modifier.padding(vertical = 8.dp),
                    enabled = !permissionStates[index]
                ) {
                    Text("Solicitar permisos")
                }
            }
            Button(
                onClick = onPermissionGranted,
                enabled = permissionStates.all { it }
            ) {
                Text("Continuar")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PermissionPreview() {
    DANP_ArtGalleryTheme {
        PermissionScreen(onPermissionGranted = {})
    }
}
