package com.keagan.finalapp.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
fun AfterSplashScreen(onLogin: () -> Unit, onSignUp: () -> Unit) {
    AppBackground {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(R.drawable.splash), contentDescription = null, contentScale = ContentScale.Fit)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onLogin, modifier = Modifier.fillMaxWidth()) { Text("Log in") }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(onClick = onSignUp, modifier = Modifier.fillMaxWidth()) { Text("Sign up") }
        }
    }
}
