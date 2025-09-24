package com.keagan.finalapp.ui.theme.foundation

import androidx.compose.ui.graphics.Color

// ---- Tags used on To-Do cards
enum class TaskTag { Study, Personal, Urgent }

// ---- Note sticky colours (if/when you need them across screens)
enum class NoteColor { Peach, Mint, Blue, Lavender, Lemon }

// Swatch model used to render pastel chip + text colour
data class Swatch(val label: String, val bg: Color, val tint: Color)

// Pastel chips to match your mockups
fun swatch(tag: TaskTag): Swatch = when (tag) {
    TaskTag.Study    -> Swatch("study",   Color(0xFFE8F6EF), Color(0xFF2E8F6F))
    TaskTag.Personal -> Swatch("personal",Color(0xFFEFF3FD), Color(0xFF55627C))
    TaskTag.Urgent   -> Swatch("urgent",  Color(0xFFFEEBED), Color(0xFFB85F6E))
}

// Optional helpers for notes if needed elsewhere
fun noteBg(color: NoteColor): Color = when (color) {
    NoteColor.Peach    -> Color(0xFFFFE3E3)
    NoteColor.Mint     -> Color(0xFFE7F7EF)
    NoteColor.Blue     -> Color(0xFFEAF2FF)
    NoteColor.Lavender -> Color(0xFFF0EAFE)
    NoteColor.Lemon    -> Color(0xFFFFF5CC)
}
fun noteTint(color: NoteColor): Color = Color(0xFF575A65)
