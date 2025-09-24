package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.R
import com.keagan.finalapp.ui.theme.background.AppBackground

@Composable
fun SplashScreen(onDone: () -> Unit) {
    AppBackground {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.splash),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Column(Modifier.align(Alignment.BottomCenter).padding(24.dp)) {
                Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Continue") }
            }
        }
    }
}
