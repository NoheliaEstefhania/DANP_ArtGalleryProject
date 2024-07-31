package com.example.danp_artgallery.beacon.utils.canvasLibrary.utils.models

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Person(
    modifier: Modifier = Modifier,
    positionX: Double,
    positionY: Double,
){

    Canvas(
        modifier = modifier
            .size(50.dp)
            .offset(positionX.dp, positionY.dp)
            .border(1.dp, Color.Black)
            .pointerInput(Unit) {
                detectTapGestures {
//                    updatePosition()
                }
            },
    ) {
        drawCircle(Color.Magenta)
    }
}