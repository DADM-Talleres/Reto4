package com.example.triqui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.triqui.ui.GameScreen

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface { GameScreen() }
            }
        }
    }
}