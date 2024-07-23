package com.example.danp_artgallery.screens.section

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.danp_artgallery.data.model.ExpositionAttributes

@Composable
fun ExpositionScreen(exposition: ExpositionAttributes, onNavIconPressed: () -> Unit = { }) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ExpositionHeader(
                        scrollState,
                        exposition,
                        this@BoxWithConstraints.maxHeight
                    )
                    ExpositionContent(exposition, this@BoxWithConstraints.maxHeight)
                }
            }
        }
    }
}

@Composable
private fun ExpositionHeader(
    scrollState: ScrollState,
    exposition: ExpositionAttributes,
    containerHeight: Dp
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

}

@Composable
private fun ExpositionContent(exposition: ExpositionAttributes, containerHeight: Dp) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Title(exposition)

        exposition.expositions.forEach { expositionDetail ->
            ExpositionProperty(expositionDetail)
        }

        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun Title(
    exposition: ExpositionAttributes
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Text(
            text = exposition.title,
            modifier = Modifier.size(32.dp),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ExpositionProperty(value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        val style = if (isLink) {
            MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
        } else {
            MaterialTheme.typography.titleMedium
        }
        Text(
            text = value,
            modifier = Modifier.size(24.dp),
            style = style
        )
    }
}


