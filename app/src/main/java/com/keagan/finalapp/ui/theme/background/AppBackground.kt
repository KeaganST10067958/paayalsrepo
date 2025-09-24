package com.keagan.finalapp.ui.theme.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import com.keagan.finalapp.ui.theme.GradBlue
import com.keagan.finalapp.ui.theme.GradPink

@Composable
fun AppBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(GradPink, GradBlue), start = Offset.Zero, end = Offset(1500f, 1500f)))
    ) { content() }
}
