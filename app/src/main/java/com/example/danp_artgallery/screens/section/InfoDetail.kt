package com.example.danp_artgallery.screens.section

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

data class ArtGallery(
    @DrawableRes val imageResource:Int,
    val title: String,
    val detail: String,
    val direction: String,
    val information: List<String>

)
val information = ArtGallery(R.drawable.ccunsalocal,
    title = "CENTRO CULTURAL DE LA UNSA\n",
    detail = "Lorem ipsum dolor sit amet consectetur adipiscing elit morbi ut hac in primis feugiat penatibus consequat, aenean malesuada leo mattis molestie justo cursus iaculis nisl eget fusce nascetur nunc.\n",
    direction = "Ubication: Santa Catalina 101, Arequipa 04001\n",
    listOf("Availability:\n", "Monday to Friday: 8 a.m. – 8:15 p.m.\n", "Saturday: 9:30 a.m. – 4:15 p.m.\n", "Sunday: Closed\n"))
@Composable
fun InfoDetailFunction(information: ArtGallery) {
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
                painter = painterResource(id = information.imageResource),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = information.title, style = typography.titleSmall)
            Text(text= information.detail, style = typography.titleSmall)
            Text(text=information.direction, style = typography.titleSmall)

            for (item in information.information) {
                Text(text = item, style = typography.bodySmall)
            }
        }
    }
}

@Composable
fun InformationDetailView(artGallery: ArtGallery) {
    LazyColumn {
        item {
            InfoDetailFunction(information = artGallery)
        }
    }
}

@Preview
@Composable
fun InfoPreview(){
    InfoDetailFunction(information)
}