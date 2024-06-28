package com.example.danp_artgallery.map.procedures

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import com.example.danp_artgallery.map.models.Room

@Composable
fun DrawRooms(rooms: List<Room>){
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    val roomRects = remember { mutableStateListOf<RectF>()}

    DisposableEffect(Unit) {
        onDispose {
            // Reset the selected room when the composable is disposed
            selectedRoom = null
        }
    }

    // Defining element colors
    var lineColor = MaterialTheme.colorScheme.outline
    var textColor = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = Dp(10f))
        .background(MaterialTheme.colorScheme.background)
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                roomRects.forEachIndexed { index, rect ->
                    if (rect.contains(offset.x, offset.y) && selectedRoom == null) {
                        selectedRoom = rooms[index]
                    }
                }
            }
        }
    ) {
        // Getting the limits
        val canvasWidth = size.width
        val canvasHeight = size.height

        if (selectedRoom == null){
            // Getting room coordinates limits
            val (minX, minY, maxX, maxY) = findRoomBounds(rooms)

            // Calculating the scale
            val scaleX = canvasWidth / (maxX - minX)
            val scaleY = canvasHeight / (maxY - minY)
            val scale = minOf(scaleX, scaleY) // Getting the less value

            // Calculating the offset to center
            val offsetX = (canvasWidth - (maxX - minX) * scale) / 2 - minX * scale
            val offsetY = (canvasHeight - (maxY - minY) * scale) / 2 - minY * scale

            // Line thickness
            val lineWidth = Dp(2f).toPx()

            roomRects.clear() // Clear previous rectangles

            // Drawing the rooms
            for (room in rooms) {
                drawRoom(room, scale, offsetX, offsetY, lineWidth, roomRects, lineColor, textColor)
            }
        } else {
            // Draw only the selected room
            val (minX, minY, maxX, maxY) = findRoomBounds(listOf(selectedRoom!!))

            // Calculating the scale to fit the canvas
            val scaleX = canvasWidth / (maxX - minX)
            val scaleY = canvasHeight / (maxY - minY)
            val scale = minOf(scaleX, scaleY) // Getting the less value

            // Calculating the offset to center
            val offsetX = (canvasWidth - (maxX - minX) * scale) / 2 - minX * scale
            val offsetY = (canvasHeight - (maxY - minY) * scale) / 2 - minY * scale

            // Line thickness
            val lineWidth = Dp(2f).toPx()

            drawRoom(
                selectedRoom!!,
                scale,
                offsetX,
                offsetY,
                lineWidth,
                roomRects,
                lineColor,
                textColor
            )
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

fun DrawScope.drawRoom(
    room: Room,
    scale: Float,
    offsetX: Float,
    offsetY: Float,
    lineWidth: Float,
    roomRects: MutableList<RectF>,
    lineColor: Color,
    textColor: Color
) {
    val points = room.points.map { point ->
        Offset(
            x = point.x * scale + offsetX,
            y = point.y * scale + offsetY
        )
    }

    // Draw the rectangle (room)
    drawLine(lineColor, start = points[0], end = points[1], strokeWidth = lineWidth)
    drawLine(lineColor, start = points[1], end = points[2], strokeWidth = lineWidth)
    drawLine(lineColor, start = points[2], end = points[3], strokeWidth = lineWidth)
    drawLine(lineColor, start = points[3], end = points[0], strokeWidth = lineWidth)

    // Calculate the center of the room
    val centerX = (points[0].x + points[2].x) / 2
    val centerY = (points[0].y + points[2].y) / 2

    // Draw the room name at the center
    drawContext.canvas.nativeCanvas.drawText(
        room.name,
        centerX,
        centerY,
        android.graphics.Paint().apply {
            color = textColor.toArgb()
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = Dp(12f).toPx()
        }
    )

    // Store the rectangle bounds
    roomRects.add(RectF(points[0].x, points[0].y, points[2].x, points[2].y))
}