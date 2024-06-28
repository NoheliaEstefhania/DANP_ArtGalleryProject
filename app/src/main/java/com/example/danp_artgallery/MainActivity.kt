package com.example.danp_artgallery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.danp_artgallery.navigation.AppNavigation

class MainActivity : ComponentActivity() {
//    lateinit var beaconReferenceApplication: BeaconReferenceApplication

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            beaconReferenceApplication = application as BeaconReferenceApplication

            AppNavigation(this, this)
        }
    }
    companion object {
        const val TAG = "MainActivity"
    }
}