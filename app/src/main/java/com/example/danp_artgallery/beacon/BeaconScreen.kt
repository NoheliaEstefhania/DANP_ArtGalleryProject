package com.example.danp_artgallery.beacon

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.example.danp_artgallery.R
import com.example.danp_artgallery.beacon.utils.BeaconReferenceApplication
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier


private const val title = "BEACON"


@Composable
fun BeaconList(beacons: List<String>) {
    Log.d("LIST", beacons.toString())
    LazyColumn {
        items(beacons) { beaconInfo  ->
            Text(text = beaconInfo, style = typography.bodySmall)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BeaconScreen(context: Context, lifecycleOwner: ComponentActivity, app: BeaconReferenceApplication) {
    val permissionsHelper = PermissionsHelper(context)
    val permissionGroups by remember { mutableStateOf(permissionsHelper.beaconScanPermissionGroupsNeeded()) }
    val beaconsListText = remember { mutableStateOf(emptyList<String>())}
    val beaconCountText = remember { mutableStateOf("--------")}
//    val beaconViewModel = viewModel<BeaconViewModel>(
//        factory = object : ViewModelProvider.Factory {
//            @RequiresApi(Build.VERSION_CODES.O)
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return BeaconViewModel(
//                    context = context,
//                    beaconManager = BeaconManager.getInstanceForApplication(context),
//                ) as T
//            }
//        }
//    )
    val region = app.region
    val rangingText = remember { mutableStateOf("Stop Ranging")}
    val monitoringText = remember { mutableStateOf("Stop Monitoring")}
    var alertDialog: AlertDialog? = null
    val monitoringObserver = Observer<Int> { state ->
        var dialogTitle = "Beacons detected"
        var dialogMessage = "didEnterRegionEvent has fired"
        var stateString = "inside"
        if (state == MonitorNotifier.OUTSIDE) {
            dialogTitle = "No beacons detected"
            dialogMessage = "didExitRegionEvent has fired"
            stateString == "outside"
            beaconCountText.value = "Outside of the beacon region -- no beacons detected"
            beaconsListText.value = arrayListOf("1", "2"," 3")
        }
        else {
            beaconCountText.value = "Inside the beacon region."
        }
        Log.d("BeaconScreen", "monitoring state changed to : $stateString")
        val builder =
            AlertDialog.Builder(context)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(android.R.string.ok, null)
        alertDialog?.dismiss()
        alertDialog = builder.create()
        alertDialog?.show()
    }

    val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        Log.d("BeaconScreen", "Ranged: ${beacons.count()} beacons")
        if (BeaconManager.getInstanceForApplication(context).rangedRegions.size > 0) {
            beaconCountText.value = "Ranging enabled: ${beacons.count()} beacon(s) detected"
            val sortedBeacons = beacons.sortedBy { it.distance }
                .map { "${it.id1}\nid2: ${it.id2} id3:  rssi: ${it.rssi}\nest. distance: ${it.distance} m" }
            beaconsListText.value = sortedBeacons
        }
    }


    val regionViewModel = BeaconManager.getInstanceForApplication(context).getRegionViewModel(region)
    // observer will be called each time the monitored regionState changes (inside vs. outside region)
    regionViewModel.regionState.observe(lifecycleOwner, monitoringObserver)
    // observer will be called each time a new list of beacons is ranged (typically ~1 second in the foreground)
    regionViewModel.rangedBeacons.observe(lifecycleOwner, rangingObserver)




    @RequiresApi(Build.VERSION_CODES.O)
    fun rangingButtonTapped() {
        val beaconManager = BeaconManager.getInstanceForApplication(context)
        if (beaconManager.rangedRegions.size == 0) {
            beaconManager.startRangingBeacons(region)
            rangingText.value = "Stop Ranging"
            beaconCountText.value = "Ranging enabled -- awaiting first callback"
        }
        else {
            beaconManager.stopRangingBeacons(region)
            rangingText.value = "Start Ranging"
            beaconCountText.value = "Ranging disabled -- no beacons detected"
            beaconsListText.value = emptyList()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monitoringButtonTapped() {
        var dialogTitle = ""
        var dialogMessage = ""
        val beaconManager = BeaconManager.getInstanceForApplication(context)
        if (beaconManager.monitoredRegions.size == 0) {
            beaconManager.startMonitoring(region)
            dialogTitle = "Beacon monitoring started."
            dialogMessage = "You will see a dialog if a beacon is detected, and another if beacons then stop being detected."
            monitoringText.value = "Stop Monitoring"
        }
        else {
            beaconManager.stopMonitoring(region)
            dialogTitle = "Beacon monitoring stopped."
            dialogMessage = "You will no longer see dialogs when beacons start/stop being detected."
            monitoringText.value = "Start Monitoring"
        }
        val builder =
            AlertDialog.Builder(context)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(android.R.string.ok, null)
        alertDialog?.dismiss()
        alertDialog = builder.create()
        alertDialog?.show()

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
                val lifecycleState = lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
                LaunchedEffect (lifecycleState){
                    if (BeaconManager.getInstanceForApplication(context).monitoredRegions.size == 0) {
                        app.setupBeaconScanning()
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp) // Puedes ajustar el padding aquí si prefieres que sea menor
                        .padding(top = paddingValues.calculateTopPadding()),  // Mantiene solo el padding superior necesario para evitar solapamiento                contentAlignment = Alignment.Center
                ) {
                    Column {
                        BeaconList(beacons = beaconsListText.value)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Row (
                        modifier =
                        Modifier.align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ){
                        Button(
                            onClick = {
                                monitoringButtonTapped()
                            }
                        ) {
                            Text(monitoringText.value)
                        }
                        Button(
                            onClick = {
                                rangingButtonTapped()
                            }
                        ) {
                            Text(rangingText.value)
                        }
                    }
                }
            } else {
                BeaconScanPermissionsScreen()
            }
        }
    )

}


