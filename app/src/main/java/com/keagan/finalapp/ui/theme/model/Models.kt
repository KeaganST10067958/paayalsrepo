package com.keagan.finalapp.ui.theme.model

import androidx.compose.ui.graphics.Color

// One enum only. Delete any other NoteColor files to avoid redeclaration.
enum class NoteColor(val swatch: Color) {
    Peach(Color(0xFFFFE1D6)),
    Mint(Color(0xFFE8F6EF)),
    Blue(Color(0xFFEFF3FD)),
    Lav(Color(0xFFEDEBFB)),
    Lemon(Color(0xFFFFF4C4)),
}

// Shared UI models
data class Task(
    val id: Long,
    val title: String,
    val note: String = "",
    val tag: String = "study",
    val done: Boolean = false,
)

data class EventItem(
    val id: Long,
    val time: String,
    val title: String,
)

data class StickyNote(
    val id: Long,
    val text: String,
    val color: NoteColor,
)
