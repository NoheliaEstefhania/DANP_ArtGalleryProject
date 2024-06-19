package com.example.danp_artgallery.map.collection

import android.content.Context
import com.example.danp_artgallery.map.models.Rooms
import com.google.gson.Gson
import java.io.IOException

fun collectRoomDataFromJSON(context: Context, fileName: String): String? {
    return try{
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ex: IOException){
        ex.printStackTrace()
        null
    }
}

fun parseRoomsFromJSON(jsonString: String): Rooms {
    return Gson().fromJson(jsonString, Rooms::class.java)
}