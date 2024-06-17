package com.example.danp_artgallery.screens.section


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R


data class Exposition(
    @DrawableRes val imageResource:Int,
    val title: String,
    val expositions: List<String>
)
val expositionList = listOf(Exposition(R.drawable.ccunsa, title = "CARPINTERO DE NIDOS\n", listOf("De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n", "Daniel Gallegos\n")),
    Exposition(R.drawable.ccunsa, title = "CARPINTERO DE NIDOS\n", listOf("De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n", "Daniel Gallegos\n")),
    Exposition(R.drawable.ccunsa, title = "CARPINTERO DE NIDOS\n", listOf("De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n", "Daniel Gallegos\n")),
    Exposition(R.drawable.ccunsa, title = "CARPINTERO DE NIDOS\n", listOf("De los hijos que se van. Daniel Gallegos. Daniel Gallegos. Centro Cultural UNSA. Galería I.Centro Cultural UNSA.\n", "Daniel Gallegos\n")))

@Composable
fun ExpositionsDetailFunction(exposition: Exposition) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))

            Image(
                painter = painterResource(id = exposition.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = exposition.title, style = typography.titleSmall)

            for (item in exposition.expositions) {
                Text(text = item, style = typography.bodySmall)
            }
        }
    }
}

@Composable
fun ExpositionList(expositionList: List<Exposition>) {
    LazyColumn {
        items(expositionList) { item ->
            ExpositionsDetailFunction(exposition = item)
        }
    }
}

@Preview
@Composable
fun ExpositionPreview(){
    ExpositionsDetailFunction(expositionList[0])
}