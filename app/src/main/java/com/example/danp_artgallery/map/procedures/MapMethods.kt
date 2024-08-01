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
                val galleryPos1 = LatLng(-16.397783710084095, -71.53746375370153) // centro cultural
                val galleryPos2 = LatLng(-16.39935655143182, -71.53748900470404) // museo santuarios andinos
                val galleryPos3 = LatLng(-16.395918874118905, -71.53663069785293) //monasterio

                val marker = googleMap.addMarker(
                    MarkerOptions().position(galleryPos1).title("Art Gallery")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(galleryPos1, 15f))
                googleMap.setOnMarkerClickListener {
                    onMarkerClick()
                    true
                }
            })
        }
    })
}