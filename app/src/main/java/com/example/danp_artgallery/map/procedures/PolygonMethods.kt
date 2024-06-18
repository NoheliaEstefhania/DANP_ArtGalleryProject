package com.example.danp_artgallery.map.procedures

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.danp_artgallery.map.models.Room

@Composable
fun drawRooms(rooms: List<Room>){
    Canvas(modifier = Modifier.fillMaxSize()) {
        rooms.forEach { room ->
            drawRoom(room)
        }
    }
}

fun DrawScope.drawRoom(room: Room) {
    val path = Path().apply {
        if (room.points.isNotEmpty()) {
            val firstPoint = room.points[0]
            moveTo(firstPoint.x, firstPoint.y)
            room.points.drop(1).forEach { point ->
                lineTo(point.x, point.y)
            }
            lineTo(firstPoint.x, firstPoint.y) // Close the polygon
        }
    }
    drawPath(path, Color.Blue)
}