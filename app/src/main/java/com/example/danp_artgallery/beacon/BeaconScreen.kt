package com.example.danp_artgallery.beacon

import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.danp_artgallery.beacon.utils.PermissionsHelper
import com.example.danp_artgallery.beacon.utils.canvasLibrary.ui.theme.DANP_ArtGalleryBeaconTheme
import com.example.danp_artgallery.navigation.CustomTopBar


private const val title = " "

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BeaconScreen(
    context: Context,
    lifecycleOwner: ComponentActivity,
    navController: NavController,
) {
    val permissionsHelper = PermissionsHelper(context)
    val permissionGroups by remember {
        mutableStateOf(permissionsHelper.beaconScanPermissionGroupsNeeded())
    }
    DANP_ArtGalleryBeaconTheme {
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
                        modifier = Modifier.align(Alignment.CenterHorizontally).offset(y = 85.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la fila de íconos y el texto, ajusta el valor según sea necesario

                }
            },
            content = { paddingValues ->
                paddingValues.toString()
                if (allPermissionGroupsGranted(context, permissionGroups)) {
                    BeaconScan(context = context, lifecycleOwner)
                } else {
                    BeaconScanPermissionsScreen(navController)
                }
            }
        )
    }
}


