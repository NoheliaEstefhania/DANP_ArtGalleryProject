package com.example.danp_artgallery.search

import android.net.wifi.ScanResult
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.danp_artgallery.R
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.navigation.CustomTopBar
import com.example.danp_artgallery.navigation.Screens
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


private const val title = "LOOK UP AN PAINT"
private val description = "Learn more by using QR code\nposted near to the paint"

@Composable
fun SearchScreen(navController: NavController){

    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result: ScanIntentResult ->
        result.contents?.let {
            handleScanResult(it, navController)
        }
    }
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                CustomTopBar(navController = navController)

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),  // Esto agrega padding para evitar que el contenido se solape con la topBar.
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.qr_app),
                    contentDescription = "Search Button",
                    modifier = Modifier
                        .clickable {
                            barcodeLauncher.launch(ScanOptions().apply {
                                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                                setPrompt("Scan a QR code")
                                setCameraId(0)
                                setBeepEnabled(false)
                                setOrientationLocked(false)
                            })
                        }
                )
            }
        }
    )
}
// Función para manejar el resultado del escaneo
private fun handleScanResult(contents: String, navController: NavController) {
    val id = extractIdFromUrl(contents) // Implementa esta función para extraer el ID del URL
    // Navegar a la pantalla de detalles de la pintura
    navController.navigate("${ Screens.PaintingDetailScreen.name}/$id")
}

// Función para extraer el ID del URL
private fun extractIdFromUrl(url: String): Int {
    return url.substringAfterLast('/').toInt()
}

@Preview
@Composable
fun SearchPreview(){
    SearchScreen(navController = rememberNavController())
}