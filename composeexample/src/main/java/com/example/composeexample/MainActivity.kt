package com.example.composeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composeexample.ui.ComposeExampleApp
import com.example.composeexample.ui.theme.AccessAndroidExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appContainer = (application as ComposeExampleApplication).container

        setContent {
            AccessAndroidExampleTheme {
                ComposeExampleApp(
                    appContainer = appContainer
                )
            }
        }
    }
}
