package com.example.danp_artgallery.beacon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.beacon.utils.canvasLibrary.utils.Point
import com.example.danp_artgallery.beacon.utils.canvasLibrary.utils.models.Person
import com.example.danp_artgallery.beacon.utils.canvasLibrary.utils.models.Room

@Composable
fun PositionTest(trilateration: String, pointXPosition: Double, pointYPosition: Double){
    val points = loadData()
    Column {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
        ){
            Text(text = trilateration)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
                // TODO: Add offset
                .offset(-45.dp)
        ) {
            Room(
                data = points,
                modifier = Modifier
                    .fillMaxSize()
            )
            Person(
                modifier = Modifier,
                positionX = pointXPosition,
                positionY = pointYPosition
            )
        }
    }
}

@Composable
fun loadData(): ArrayList<Point> {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = with(density) { configuration.screenHeightDp.dp.roundToPx() }
    val factor = screenHeight * 0.05f

    return arrayListOf(
        Point(0.5f * factor, 0.5f * factor), // inicio
        Point(9.2f * factor, 0.5f * factor), // x
        Point(9.2f * factor, 9.2f * factor),
        Point(0.5f * factor, 9.2f * factor), // y
        Point(0.5f * factor, 0.5f * factor)
    )
}