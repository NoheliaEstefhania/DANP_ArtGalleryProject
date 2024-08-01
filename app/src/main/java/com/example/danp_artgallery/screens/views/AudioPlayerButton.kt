package com.example.danp_artgallery.screens.views

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat

@Composable
fun AudioPlayerButton(url:String) {
    val context = LocalContext.current
    var isBluetoothEnabled by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val mediaPlayer = remember {
        MediaPlayer().apply {
            setDataSource(url)
            prepareAsync() // Usar prepareAsync para evitar bloqueos
            setOnPreparedListener {
                // El MediaPlayer está preparado para la reproducción
                if (isPlaying) {
                    start()
                }
            }
            setOnCompletionListener {
                // Resetea el estado de reproducción cuando el audio termina
                isPlaying = false
            }
            setOnErrorListener { _, what, extra ->
                Log.e("MediaPlayerError", "Error occurred: what=$what, extra=$extra")
                true // Retorna true si manejas el error
            }
        }
    }

    // Define the BroadcastReceiver
    val bluetoothReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        val state =
                            intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                        isBluetoothEnabled = state == BluetoothAdapter.STATE_ON
                        if (state == BluetoothAdapter.STATE_OFF && mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                            isPlaying = false
                        }
                    }
                }
            }
        }
    }

    val headsetReceiver = remember {
        object : BroadcastReceiver() {
            private var headsetConnected = false
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == AudioManager.ACTION_HEADSET_PLUG) {
                    if (intent.hasExtra("state")) {
                        if (headsetConnected && intent.getIntExtra("state", 0) == 0) {
                            headsetConnected = false;
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause()
                                isPlaying = false
                            }
                        } else if (!headsetConnected && intent.getIntExtra("state", 0) == 1) {
                            headsetConnected = true;
                        }
                    }
                }
            }
        }
    }

    val bluetoothHeadsetReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_DISCONNECTED)
                    if (state == BluetoothHeadset.STATE_DISCONNECTED && mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                        isPlaying = false
                    }
                }
            }
        }
    }

    // Register the BroadcastReceiver
    DisposableEffect(context) {
        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        context.registerReceiver(bluetoothReceiver, filter)
        onDispose {
            try {
                context.unregisterReceiver(bluetoothReceiver)
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
    }


    fun areWiredHeadphonesConnected(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.isWiredHeadsetOn
    }

    fun isBluetoothHeadsetConnected(onResult: (Boolean) -> Unit) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        bluetoothAdapter?.let {
            it.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
                @SuppressLint("MissingPermission")
                override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                    if (profile == BluetoothProfile.HEADSET) {
                        try {
                            val connectedDevices = (proxy as BluetoothHeadset).connectedDevices
                            onResult(connectedDevices.isNotEmpty())
                        } catch (e: Exception){
                            Log.d("meirda","activa bluetoto")
                        }
                        it.closeProfileProxy(BluetoothProfile.HEADSET, proxy)
                    }
                }

                override fun onServiceDisconnected(profile: Int) {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause()
                        isPlaying = false
                    }
                }
            }, BluetoothProfile.HEADSET)
        } ?: onResult(false)
    }

    // Function to check if any type of headphones are connected
    fun areHeadphonesConnected(onResult: (Boolean) -> Unit) {
        if (areWiredHeadphonesConnected()) {
            onResult(true)
        } else {
            if (isBluetoothEnabled) {
                isBluetoothHeadsetConnected(onResult)
            } else {
                onResult(false)
                Toast.makeText(context,
                    "Bluetooth is not enabled if you want connect wireless headphones",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Error") },
            text = { Text("Por favor, conecta unos audífonos para reproducir el audio.") },
            confirmButton = {
                TextButton(onClick = { showAlertDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    Button(
        onClick = {
            areHeadphonesConnected { connected ->
                if (connected) {
                    isPlaying = !isPlaying
                } else {
                    showAlertDialog = true
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = if (isPlaying) "Pause" else "Play",
            fontSize = 18.sp
        )
    }
    // Manage MediaPlayer state
    LaunchedEffect(isPlaying) {
        try {
            if (isPlaying) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.seekTo(0) // Restart if necessary
                } else {
                    mediaPlayer.start()
                }
            } else {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                }
            }
        } catch (e: Exception) {
            Log.e("AudioPlayerError", "Error while starting playback", e)
        }
    }

    // Register and unregister BroadcastReceiver for wired headset
    DisposableEffect(context) {
        val filter = IntentFilter(AudioManager.ACTION_HEADSET_PLUG)
        context.registerReceiver(headsetReceiver, filter)
        onDispose {
            try {
                context.unregisterReceiver(headsetReceiver)
            } catch (e: IllegalArgumentException) {
                // Handle the case where the receiver might not be registered
                Log.e("ReceiverError", "Receiver not registered or already unregistered.")
            }
        }
    }

    DisposableEffect(context) {
        val filter = IntentFilter(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)
        context.registerReceiver(bluetoothHeadsetReceiver, filter)
        onDispose {
            try {
                context.unregisterReceiver(bluetoothHeadsetReceiver)
            } catch (e: IllegalArgumentException) {
                Log.e("ReceiverError", "BluetoothHeadset receiver not registered or already unregistered.")
            }
        }
    }

    // Clean up MediaPlayer when composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}