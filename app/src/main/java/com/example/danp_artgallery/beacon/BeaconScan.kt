package com.example.danp_artgallery.beacon

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.example.danp_artgallery.beacon.utils.BeaconGalleryService
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier

@Composable
fun BeaconScan(
    context: Context,
    lifecycleOwner: ComponentActivity,
    paddingValues: PaddingValues
) {
    val serviceStatus = remember {
        mutableStateOf(BeaconGalleryService.isActive)
    }
    val buttonValue =
        remember {
            if (serviceStatus.value) {
                mutableStateOf("Stop Service")
            }
            else {
                mutableStateOf("Start Service")
            }
        }
    val rangingText =
        remember {
            if (BeaconGalleryService.isRanging and serviceStatus.value){
                mutableStateOf("Stop Ranging")
            } else {
                mutableStateOf("Start Ranging")
            }
        }
    val monitoringText =
        remember {
            if (BeaconGalleryService.isMonitoring and serviceStatus.value) {
                mutableStateOf("Stop Monitoring")
            } else {
                mutableStateOf("Start Monitoring")
            }
        }

    val beaconsListText = remember { mutableStateOf(emptyList<String>()) }
    val beaconCountText = remember { mutableStateOf("--------") }
    var alertDialog: AlertDialog? = null
    val monitoringObserver = Observer<Int> { state ->
        var dialogTitle = "Beacons detected"
        var dialogMessage = "didEnterRegionEvent has fired"
        var stateString = "inside"
        if (state == MonitorNotifier.OUTSIDE) {
            dialogTitle = "No beacons detected"
            dialogMessage = "didExitRegionEvent has fired"
            stateString = "outside"
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
        if (BeaconManager.getInstanceForApplication(context).rangedRegions.isNotEmpty()) {
            beaconCountText.value = "Ranging enabled: ${beacons.count()} beacon(s) detected"
            val sortedBeacons = beacons.sortedBy { it.distance }
                .map {
                    "${
                        it.id1
                    }\nid2: ${
                        it.id2
                    } id3:  rssi: ${
                        it.rssi
                    }\nest. distance: ${
                        it.distance
                    } m"
                }
            beaconsListText.value = sortedBeacons
        }
    }
    val region = BeaconGalleryService.region

    @RequiresApi(Build.VERSION_CODES.O)
    fun rangingButtonTapped() {
        val beaconManager = BeaconManager.getInstanceForApplication(context)
        if (beaconManager.rangedRegions.isEmpty()) {
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
        val dialogTitle: String
        val dialogMessage: String
        val beaconManager = BeaconManager.getInstanceForApplication(context)
        if (beaconManager.monitoredRegions.isEmpty()) {
            beaconManager.startMonitoring(region)
            dialogTitle = "Beacon monitoring started."
            dialogMessage = "You will see a dialog if a beacon is detected, " +
                    "and another if beacons then stop being detected."
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

    // on below line we are creating a column,
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 125.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                BeaconList(beacons = beaconsListText.value)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp) // Puedes ajustar el padding aquí si prefieres que sea menor
                    .padding(top = 100.dp),  // Mantiene solo el padding superior necesario para evitar solapamiento                contentAlignment = Alignment.Center
            ) {
                Row (modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                ){
                    Button(
                        onClick = {

                        }
                    ) {
                        Text(
                            text = "Test Positioning",
                            modifier = Modifier.padding(10.dp),
                            color = Color.White,
                        )
                    }
                }
                Row (
                    modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 60.dp)
                ){
                    Button(
                        onClick = {
                            monitoringButtonTapped()
                        },
                        enabled = serviceStatus.value
                    ) {
                        Text(monitoringText.value)
                    }
                    Button(
                        onClick = {
                            rangingButtonTapped()
                        },
                        enabled = serviceStatus.value
                    ) {
                        Text(rangingText.value)
                    }
                }
                Row (
                    modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 115.dp)
                ){
                    Button(
                        onClick = {
                            if (serviceStatus.value) {
                                // service already running
                                // stop the service
                                serviceStatus.value = !serviceStatus.value
                                buttonValue.value = "Start Service"
                                val regionViewModel = BeaconManager.getInstanceForApplication(
                                    context
                                ).getRegionViewModel(region)
                                regionViewModel.rangedBeacons.removeObservers(lifecycleOwner)
                                regionViewModel.regionState.removeObservers(lifecycleOwner)
                                context.stopService(Intent(context, BeaconGalleryService::class.java))
                                rangingText.value = "Start Ranging"
                                monitoringText.value = "Start Monitoring"
                            } else {
                                // service not running start service.
                                serviceStatus.value = !serviceStatus.value
                                buttonValue.value = "Stop Service"
                                // starting the service
                                context.startService(Intent(context, BeaconGalleryService::class.java))
                                val regionViewModel = BeaconManager.getInstanceForApplication(
                                    context
                                ).getRegionViewModel(region)
                                rangingText.value = "Stop Ranging"
                                monitoringText.value = "Stop Monitoring"
                                // observer will be called each time the monitored regionState changes (inside vs. outside region)
                                regionViewModel.regionState.observe(lifecycleOwner, monitoringObserver)
                                // observer will be called each time a new list of beacons is ranged (typically ~1 second in the foreground)
                                regionViewModel.rangedBeacons.observe(lifecycleOwner, rangingObserver)
                            }
                        }
                    ) {
                        Text(
                            text = buttonValue.value,
                            modifier = Modifier.padding(10.dp),
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}