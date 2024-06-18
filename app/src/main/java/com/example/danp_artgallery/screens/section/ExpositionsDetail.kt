package com.example.danp_artgallery.screens.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.danp_artgallery.model.DataProvider
import com.example.danp_artgallery.model.Exposition


@Composable
fun ExpositionsDetailFunction(exposition: Exposition, navigateToPaintDetail: (Exposition)-> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).clickable { navigateToPaintDetail(exposition) }
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
fun ExpositionList(expositionList: List<Exposition>, navigateToPaintDetail: (Exposition) -> Unit) {
    LazyColumn {
        items(expositionList) { item ->
            ExpositionsDetailFunction(exposition = item, navigateToPaintDetail = navigateToPaintDetail)
        }
    }
}

@Preview
@Composable
fun ExpositionPreview() {
    ExpositionsDetailFunction(
        exposition = DataProvider.expositionList[0],
        navigateToPaintDetail = {}
    )
}