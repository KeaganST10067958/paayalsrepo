package com.keagan.finalapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = BrandPink
)

@Composable
fun FinalAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        content = content
    )
}
