package com.keagan.finalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.keagan.finalapp.data.Repo
import com.keagan.finalapp.ui.theme.AppNavGraph
import com.keagan.finalapp.ui.theme.FinalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = Repo()

        setContent {
            FinalAppTheme {
                AppNavGraph(repo = repo)
            }
        }
    }
}
