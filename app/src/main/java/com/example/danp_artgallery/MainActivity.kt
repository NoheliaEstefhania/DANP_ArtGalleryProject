package com.example.danp_artgallery

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.danp_artgallery.beacon.utils.BeaconReferenceApplication
import com.example.danp_artgallery.navigation.AppNavigation
import com.example.danp_artgallery.model.Exposition
import com.example.danp_artgallery.ui.theme.DANP_ArtGalleryTheme

class MainActivity : ComponentActivity() {
    lateinit var beaconReferenceApplication: BeaconReferenceApplication

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            beaconReferenceApplication = application as BeaconReferenceApplication

            AppNavigation(this, this,beaconReferenceApplication)
        }
    }
    companion object {
        val TAG = "MainActivity"
    }
}

@Composable
fun MyApp(navigateToProfile: (Exposition) -> Unit) {

}
