package com.example.danp_artgallery.beacon

import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.example.danp_artgallery.beacon.utils.BeaconConfig.Companion.ROOM_HEIGHT
import com.example.danp_artgallery.beacon.utils.BeaconConfig.Companion.ROOM_LENGTH
import com.example.danp_artgallery.beacon.utils.BeaconConfig.Companion.allowedUUIDs
import com.example.danp_artgallery.beacon.utils.BeaconConfig.Companion.positions
import com.example.danp_artgallery.beacon.utils.BeaconConfig.Companion.trilaterate
import com.example.danp_artgallery.beacon.utils.BeaconGalleryService
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.Region

@Composable
fun BeaconList(beacons: List<String>, count: String) {
    Text(text = count, style = MaterialTheme.typography.bodySmall)
    LazyColumn {
        items(beacons) { beaconInfo  ->
            Text(text = beaconInfo, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun BeaconScan(
    context: Context,
    lifecycleOwner: ComponentActivity
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
    val showBeacons = remember {
        mutableStateOf(true)
    }
    val trilaterationText = remember { mutableStateOf("-") }
    val pointXPosition = remember { mutableDoubleStateOf(ROOM_LENGTH - 300) }
    val pointYPosition = remember{ mutableDoubleStateOf(ROOM_HEIGHT - 300) }
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
            val beaconsList = beacons.toList()
            if(
                beaconsList.find { it.id1.toString() == allowedUUIDs[0] }?.distance != null &&
                beaconsList.find { it.id1.toString() == allowedUUIDs[1] }?.distance != null &&
                beaconsList.find { it.id1.toString() == allowedUUIDs[2] }?.distance != null
            ){
                val distances = doubleArrayOf(
                    beaconsList.find { it.id1.toString() == allowedUUIDs[0] }?.distance!!.toDouble(),
                    beaconsList.find { it.id1.toString() == allowedUUIDs[1] }?.distance!!.toDouble(),
                    beaconsList.find { it.id1.toString() == allowedUUIDs[2] }?.distance!!.toDouble(),
                )
                trilaterate(positions, distances, pointXPosition, pointYPosition, trilaterationText)
            }
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
        Spacer(modifier = Modifier.height(50.dp))
        Row {
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
        Row {
            Button(
                onClick = {
                    showBeacons.value = !showBeacons.value
                },
            ) {
                Text(
                    text = "Test Positioning",
                )
            }
            BluetoothButton(
                serviceStatus,
                buttonValue,
                region,
                lifecycleOwner,
                rangingText,
                monitoringText,
                monitoringObserver,
                rangingObserver,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if(showBeacons.value){
                BeaconList(beacons = beaconsListText.value, count = beaconCountText.value)
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                PositionTest(
                    trilateration = trilaterationText.value,
                    pointXPosition = pointXPosition.doubleValue,
                    pointYPosition = pointYPosition.doubleValue
                )
            }
        }
    }
}

@Composable
fun BluetoothButton(
    serviceStatus: MutableState<Boolean>,
    buttonValue: MutableState<String>,
    region: Region,
    lifecycleOwner: ComponentActivity,
    rangingText: MutableState<String>,
    monitoringText: MutableState<String>,
    monitoringObserver: Observer<Int>,
    rangingObserver: Observer<Collection<Beacon>>
    ){
    val context = LocalContext.current
    var isBluetoothEnabled by remember { mutableStateOf(false) }
    var isLocationEnabled by remember { mutableStateOf(false) }

    // Define the BroadcastReceiver
    val bluetoothLocationReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        val state =
                            intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                        isBluetoothEnabled = state == BluetoothAdapter.STATE_ON
                    }
                    LocationManager.PROVIDERS_CHANGED_ACTION -> {
                        val locationManager =
                            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                        isLocationEnabled = isGpsEnabled || isNetworkEnabled
                    }
                }
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(context) {
        val filter = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
            addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        }
        context.registerReceiver(bluetoothLocationReceiver, filter)
        onDispose {
            try {
                context.unregisterReceiver(bluetoothLocationReceiver)
            } catch (e: IllegalArgumentException) {
                // Handle the case where the receiver might not be registered
                Log.e("ReceiverError", "Receiver not registered or already unregistered.")
            }
        }
    }

    // Initial check of Bluetooth state
    LaunchedEffect(Unit) {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        isBluetoothEnabled = bluetoothAdapter?.isEnabled == true
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        isLocationEnabled = isGpsEnabled || isNetworkEnabled
    }
    Button(
        onClick = {
            if (isBluetoothEnabled) {
                if(isLocationEnabled) {
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
                } else {
                    Toast.makeText(context, "Location is not enabled", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show()
            }
        },
    ) {
        Text(
            text = buttonValue.value,
        )
    }
}