package com.example.danp_artgallery.screens.section

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.danp_artgallery.model.ExpositionAttributes
class ExpositionActivity : AppCompatActivity() {

    private val exposition: ExpositionAttributes by lazy {
        intent?.getSerializableExtra(EXPOSITION_ID) as ExpositionAttributes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                ExpositionScreen(exposition)
        }
    }

    companion object {
        private const val EXPOSITION_ID = "exposition_id"
        fun newIntent(context: Context, exposition: ExpositionAttributes) =
            Intent(context, ExpositionActivity::class.java).apply {
                putExtra(EXPOSITION_ID, exposition)
            }
    }
}
