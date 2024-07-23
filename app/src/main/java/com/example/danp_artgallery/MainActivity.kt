package com.example.danp_artgallery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.danp_artgallery.navigation.AppNavigation
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DANP_ArtGalleryTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation(this, this)
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
