package com.example.danp_artgallery.screens.views

import android.annotation.SuppressLint
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
@Composable
fun AudioPlayerButton(url:String) {
    val context = LocalContext.current
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

    val headsetReceiver = remember {
        object : BroadcastReceiver() {
            private var headsetConnected = false
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == AudioManager.ACTION_HEADSET_PLUG) {
                    val state = intent.getIntExtra("state", -1)
                    if (state == 0) { // Headset disconnected
                        headsetConnected = false
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                            isPlaying = false
                        }
                    } else if (state == 1) { // Headset connected
                        headsetConnected = true
                        // Optionally resume playback if needed
                        if (isPlaying) {
                            mediaPlayer.start()
                        }
                    }
                }
            }
        }
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
                        val connectedDevices = (proxy as BluetoothHeadset).connectedDevices
                        onResult(connectedDevices.isNotEmpty())
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
            isBluetoothHeadsetConnected(onResult)
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Error") },
            text = { Text("Please connect headphones to play the audio.") },
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
            fontSize = 18.sp,
            color = Color.Black

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

    // Clean up MediaPlayer when composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}