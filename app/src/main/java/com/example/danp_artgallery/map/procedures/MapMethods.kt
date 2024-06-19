package com.example.danp_artgallery.map.procedures

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun ShowMap(onMarkerClick: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember { MapView(context) }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                mapView.onCreate(Bundle())
            }
            override fun onStart(owner: LifecycleOwner) {
                mapView.onStart()
            }
            override fun onResume(owner: LifecycleOwner) {
                mapView.onResume()
            }
            override fun onPause(owner: LifecycleOwner) {
                mapView.onPause()
            }
            override fun onStop(owner: LifecycleOwner) {
                mapView.onStop()
            }
            override fun onDestroy(owner: LifecycleOwner) {
                mapView.onDestroy()
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            mapView.onDestroy()
        }
    }

    AndroidView(factory = {
        mapView.apply {
            getMapAsync(OnMapReadyCallback { googleMap ->
                val sydney = LatLng(-33.852, 151.211)
                val marker = googleMap.addMarker(
                    MarkerOptions().position(sydney).title("Marker in Sydney")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

                googleMap.setOnMarkerClickListener {
                    onMarkerClick()
                    true
                }
            })
        }
    })
}