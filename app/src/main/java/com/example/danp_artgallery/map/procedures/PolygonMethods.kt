package com.example.danp_artgallery.map.procedures

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.danp_artgallery.map.models.Room

@Composable
fun drawRooms(rooms: List<Room>){
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Getting the limits
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Getting room coordinates limits
        val (minX, minY, maxX, maxY) = findRoomBounds(rooms)

        // Calculating the scale
        val scaleX = canvasWidth / (maxX - minX)
        val scaleY = canvasHeight / (maxY - minY)
        val scale = minOf(scaleX, scaleY) // Getting the less value

        // Calculating the offset to center
        val offsetX = (canvasWidth - (maxX - minX) * scale) / 2 - minX * scale
        val offsetY = (canvasHeight - (maxY - minY) * scale) / 2 - minY * scale

        // Drawing the rooms
        for (room in rooms) {
            val points = room.points.map { point ->
                Offset(
                    x = point.x * scale + offsetX,
                    y = point.y * scale + offsetY
                )
            }
            drawPolygon(points, Color.Blue)
        }
    }
}

// General class bounds
data class Bounds(val minX: Float, val minY: Float, val maxX: Float, val maxY: Float)

fun findRoomBounds(rooms: List<Room>): Bounds {
    var minX = Float.MAX_VALUE
    var minY = Float.MAX_VALUE
    var maxX = Float.MIN_VALUE
    var maxY = Float.MIN_VALUE
    for (room in rooms) {
        for (point in room.points) {
            if (point.x < minX) minX = point.x
            if (point.y < minY) minY = point.y
            if (point.x > maxX) maxX = point.x
            if (point.y > maxY) maxY = point.y
        }
    }
    return Bounds(minX, minY, maxX, maxY)
}

fun DrawScope.drawPolygon(points: List<Offset>, color: Color) {
    if (points.size < 2) return
    val path = Path().apply {
        moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size) {
            lineTo(points[i].x, points[i].y)
        }
        close()
    }
    drawPath(path, color)
}