package com.example.danp_artgallery.data.network

import com.example.danp_artgallery.data.model.Exposition
import com.example.danp_artgallery.data.model.Painting
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pictures")
    suspend fun getPaintings(): List<Painting>

    @GET("pictures/{id}")
    suspend fun getPaintingDetails(@Path("id") id: Int): Painting

    @GET("galleries/{id}/expositions")
    suspend fun getExpositions(@Path("id") id: Int): List<Exposition>

    @GET("expositions/{id}/pictures")
    suspend fun getPictures(@Path("id") id: Int): List<Painting>
    @GET("expositions/{id}")
    suspend fun getExpositionDetails(@Path("id") id: Int): Exposition

}