package com.example.danp_artgallery.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel(images: List<Int>, contentPadding: PaddingValues) {
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 0 && currentIndex > 0) {
                        coroutineScope.launch {
                            currentIndex -= 1
                        }
                    } else if (dragAmount < 0 && currentIndex < images.size - 1) {
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
            items(images.size) { index ->
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(200.dp)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CarouselPreview() {
    val images = listOf(
        R.drawable.ccunsa,
        R.drawable.carpintero_nidos,
        R.drawable.ccunsalocal,
    )

    MaterialTheme {
        Surface {
            ImageCarousel(images = images, contentPadding = PaddingValues(horizontal = 4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCarousel() {
    CarouselPreview()
}