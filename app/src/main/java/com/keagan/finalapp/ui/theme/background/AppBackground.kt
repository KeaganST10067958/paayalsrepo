package com.keagan.finalapp.ui.theme.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        // put gradient or image if you have one
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {}
        content()
    }
}
