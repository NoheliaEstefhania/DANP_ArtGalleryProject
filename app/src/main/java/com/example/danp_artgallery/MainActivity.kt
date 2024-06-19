package com.example.danp_artgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.danp_artgallery.beacon.utils.BeaconViewModel
import com.example.danp_artgallery.navigation.AppNavigation
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

class MainActivity : ComponentActivity() {
    val beaconViewModel: BeaconViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation(beaconViewModel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DANP_ArtGalleryTheme {
        Greeting("Android")
    }
}