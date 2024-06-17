package com.example.danp_artgallery.screens.section

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.R


data class Paint(
    @DrawableRes val imageResource:Int,
    val title: String,
    val descriptionTitle : String,
    val description : String,
    val authorTitle: String,
    val author: String,
    val tecniqueTitle: String,
    val tecnique: String
)

val paint = Paint(R.drawable.carpintero_nidos,

    title = "CARPINTERO DE NIDOS\n",
    descriptionTitle = "\nDescription\n",
    description = "In the high Andes of Peru lived Qhapaq, a very special llama. Unlike other llamas, Qhapaq spoke Quechua, an ancient language of the Andes. One day, he found a stone with strange inscriptions in Quechua. Intrigued, he searched for answers.\n",
    authorTitle = "Author: ",
    author = "Daniel Gallegos\n",
    tecniqueTitle = "Tecnique: ",
    tecnique = "Watercolor\n")
@Composable
fun PaintsDetailFunction(paint: Paint) {
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = paint.title,
                    style = typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Image(
                painter = painterResource(id = paint.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
            Text(text = paint.descriptionTitle, style = typography.titleSmall.copy(color = Color.Blue))
            Text(text = paint.description, style = typography.titleSmall)
            Text(text = paint.authorTitle, style = typography.titleSmall.copy(color = Color.Blue))
            Text(text = paint.author, style = typography.titleSmall)
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = paint.authorTitle,
//                    style = typography.titleSmall.copy(color = Color.Blue),
//                    modifier = Modifier.align(Alignment.Start)
//                )
//                Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el título y el autor
//                Text(
//                    text = paint.author,
//                    style = typography.titleSmall,
//                    modifier = Modifier.align(Alignment.Start)
//                )
//            }
            Text(text = paint.tecniqueTitle,style = typography.titleSmall.copy(color = Color.Blue))
            Text(text = paint.tecnique, style = typography.titleSmall)
        }
    }
}

@Composable
fun PaintDetailView(paint: Paint) {
    LazyColumn {
        item {
            PaintsDetailFunction(paint = paint)
        }
    }
}

@Preview
@Composable
fun PaintPreview(){
    PaintsDetailFunction(paint)
}