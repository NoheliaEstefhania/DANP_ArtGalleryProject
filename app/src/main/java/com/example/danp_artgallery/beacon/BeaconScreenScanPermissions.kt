package com.example.danp_artgallery.beacon

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.danp_artgallery.beacon.utils.PermissionsHelper
import com.example.danp_artgallery.navigation.Screens

@Composable
fun BeaconScanPermissionsScreen(navController: NavController) {
    val context = LocalContext.current
    val permissionsHelper = remember { PermissionsHelper(context) }
    val permissionGroups by remember {
        mutableStateOf(
            permissionsHelper.beaconScanPermissionGroupsNeeded()
        )
    }
    val continueButtonEnabled = remember { mutableStateOf(false) }
    val tag = "PermissionsScreen"

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value
            Log.d(tag, "$permissionName permission granted: $isGranted")
        }
        if(
            allPermissionGroupsGranted(
                context,
                permissionGroups = permissionGroups
            )
        ){
            continueButtonEnabled.value = true
        }
    }
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

                }

                Spacer(modifier = Modifier.height(50.dp)) // Espacio entre la fila de íconos y el texto, ajusta el valor según sea necesario

            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp) // Puedes ajustar el padding aquí si prefieres que sea menor
                    .padding(top = paddingValues.calculateTopPadding()),  // Mantiene solo el padding superior necesario para evitar solapamiento                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Permissions Needed",
                        style = typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "In order to scan for beacons, this app requires the following permissions from the operating system. Please tap each button to grant each required permission.",
                        style = typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp),
                    )

                    for (permissionGroup in permissionGroups) {
                        PermissionButton(
                            permissionGroup = permissionGroup,
                            onClick = {

                                promptForPermissions(
                                    context,
                                    requestPermissionLauncher,
                                    permissionGroup
                                )

                            },
                            modifier = Modifier.padding(bottom = 8.dp),
                        )
                    }

                    Button(
                        onClick = {
                            if (
                                allPermissionGroupsGranted(
                                    context,
                                    permissionGroups = permissionGroups
                                )
                            ) {
                                navController.navigate(Screens.BeaconScreen.name)
                            }
                        },
                        enabled = continueButtonEnabled.value,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    )
}

fun allPermissionGroupsGranted(context: Context, permissionGroups: List<Array<String>>): Boolean {
    for (permissionsGroup in permissionGroups) {
        if (!allPermissionsGranted(context, permissionsGroup)) {
            return false
        }
    }
    return true
}

@Composable
fun PermissionButton(
    permissionGroup: Array<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val allGranted = allPermissionsGranted(LocalContext.current, permissionGroup)

    Button(
        onClick = onClick,
        enabled = enabled && !allGranted,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = permissionGroup.joinToString(", "),
        )
    }
}

fun promptForPermissions(
    context: Context,
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    permissionsGroup: Array<String>
) {
    if (!allPermissionsGranted(context, permissionsGroup)) {
//        val firstPermission = permissionsGroup.first()

//        if (PermissionsHelper(context).isFirstTimeAskingPermission(firstPermission)) {
//            PermissionsHelper(context).setFirstTimeAskingPermission(firstPermission, false)
            requestPermissionLauncher.launch(permissionsGroup)
//        }
//        else {
//            val builder = AlertDialog.Builder(context)
//            builder.setTitle("Can't request permission")
//            builder.setMessage("This permission has been previously denied to this app.  In order to grant it now, you must go to Android Settings to enable this permission.")
//            builder.setPositiveButton("OK", null)
//            builder.show()
//        }
    }}

fun allPermissionsGranted(context: Context, permissionsGroup: Array<String>): Boolean {
    val permissionHelper = PermissionsHelper(context)
    for (permission in permissionsGroup) {
        if (!permissionHelper.isPermissionGranted(permission)) {
            return false
        }
    }
    return true
}