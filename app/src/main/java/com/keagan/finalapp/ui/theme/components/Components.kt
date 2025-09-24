package com.keagan.finalapp.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.keagan.finalapp.ui.theme.BrandPink
import com.keagan.finalapp.ui.theme.TextMuted

@Composable
fun BodyText(text: String, centered: Boolean = true) {
    Text(text, style = MaterialTheme.typography.bodyMedium, color = TextMuted, textAlign = if (centered) TextAlign.Center else TextAlign.Start)
}

@Composable
fun PillField(value: String, onValueChange: (String) -> Unit, placeholder: String, isPassword: Boolean = false, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp), singleLine = true
    )
}

@Composable
fun PinkCta(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().shadow(6.dp, RoundedCornerShape(22.dp), clip = false),
        colors = ButtonDefaults.buttonColors(containerColor = BrandPink),
        shape = RoundedCornerShape(22.dp)
    ) { Text(text, fontWeight = FontWeight.SemiBold) }
}

@Composable fun WhiteCta(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(onClick = onClick, modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp)) { Text(text) }
}

@Composable
fun GoogleButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(onClick = onClick, modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp)) { Text("Continue with Google") }
}

@Composable
fun Segmented(
    options: List<String>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onSelected: (Int) -> Unit
) {
    SingleChoiceSegmentedButtonRow(modifier) {
        options.forEachIndexed { i, label ->
            SegmentedButton(
                selected = i == selectedIndex,
                onClick = { onSelected(i) },
                shape = SegmentedButtonDefaults.itemShape(index = i, count = options.size),
                label = { Text(label) }
            )
        }
    }
}

@Composable fun TinyTag(text: String) { AssistChip(onClick = {}, label = { Text(text) }) }
