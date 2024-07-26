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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.danp_artgallery.R
import com.example.danp_artgallery.navigation.Screens
import com.example.danp_artgallery.screens.views.PaintDetailScreen
import kotlinx.coroutines.launch

@Composable
fun ImageCarousel(images: List<Int>, navController: NavController) {
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
                //.fillMaxSize()
        ) {
            items(images.size) { index ->
                Column(
                    modifier = Modifier
                        //.height(500.dp)
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 150.dp)
                            .border(
                                5.dp,
                                MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(12.dp)
                            )
                           /* .clickable {
                                navController.navigate(Screens.PaintDetailScreen.name)
                            }*/
                    ) {
                        Image(
                            painter = painterResource(id = images[index]),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                            /* .clickable {
                                     navController.navigate(Screens.PaintDetailScreen.name)
                             }*/
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CarouselPreview() {
    val images = listOf(
        R.drawable.carousel01,
        R.drawable.carousel02,
        R.drawable.carousel03,
    )

    MaterialTheme {
        Surface {
            ImageCarousel(images = images, navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCarousel() {
    CarouselPreview()
}