package com.example.danp_artgallery.data.network

import com.example.danp_artgallery.data.model.PaintAttributes
import retrofit2.http.GET

interface ApiService {
    @GET("pictures")
    suspend fun getPaintings(): List<PaintAttributes>
}