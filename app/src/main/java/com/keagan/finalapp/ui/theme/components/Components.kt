// app/src/main/java/com/keagan/finalapp/ui/theme/components/Components.kt
package com.keagan.finalapp.ui.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.keagan.finalapp.ui.theme.BrandPink

@Composable
fun BodyText(text: String, centered: Boolean = true) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = if (centered) TextAlign.Center else TextAlign.Start
    )
}

@Composable
fun PillField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    )
}

@Composable
fun PinkCta(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = BrandPink),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun WhiteCta(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text = text)
    }
}

@Composable
fun GoogleButton(onClick: () -> Unit) {
    // Icon removed to avoid extra dependency; keeps you building clean.
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Text("Continue with Google")
    }
}
