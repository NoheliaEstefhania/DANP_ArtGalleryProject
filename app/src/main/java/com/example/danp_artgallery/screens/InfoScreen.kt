package com.example.danp_artgallery.screens

import android.icu.text.IDNA.Info
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize


@Composable
fun InfoScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
        text = "Info Screen",
        fontFamily= FontFamily.Serif, fontSize = 22.sp
        )
    }
}