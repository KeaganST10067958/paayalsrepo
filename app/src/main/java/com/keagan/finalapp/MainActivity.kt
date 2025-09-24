// MainActivity.kt
package com.keagan.finalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.keagan.finalapp.ui.theme.AppNavGraph
import com.keagan.finalapp.ui.theme.FinalAppTheme // or your Theme() if renamed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalAppTheme {
                AppNavGraph()
            }
        }
    }
}
