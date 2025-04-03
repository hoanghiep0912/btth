package com.example.ourfirebaserealtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ourfirebaserealtime.ui.DataScreen
import com.example.ourfirebaserealtime.ui.LoginScreen
import com.example.ourfirebaserealtime.ui.theme.OurFirebaseRealtimeTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OurFirebaseRealtimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onNavigateToData = { currentScreen = "data" }
                        )
                        "data" -> DataScreen(
                            onNavigateBack = { currentScreen = "login" }
                        )
                    }
                }
            }
        }
    }
}