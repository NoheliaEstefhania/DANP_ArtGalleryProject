package com.example.danp_artgallery.home.Expositions

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.danp_artgallery.R
import com.example.danp_artgallery.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel(
    imageUrls: List<String>,
    navController: NavController,
    onImageClick: (String) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 0 && currentIndex > 0) {
                        coroutineScope.launch {
                            currentIndex -= 1
                        }
                    } else if (dragAmount < 0 && currentIndex < imageUrls.size - 1) {
                        coroutineScope.launch {
                            currentIndex += 1
                        }
                    }
                }
            }
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(imageUrls.size) { index ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 150.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                5.dp,
                                MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                onImageClick(imageUrls[index])
                            }
                    ) {
                        Image(
                            painter = rememberImagePainter(imageUrls[index]),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()

                        )
                    }
                }
            }
        }
    }
}
