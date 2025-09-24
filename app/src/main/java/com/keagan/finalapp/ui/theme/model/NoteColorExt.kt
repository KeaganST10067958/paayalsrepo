package com.keagan.finalapp.ui.theme.model

import androidx.compose.ui.graphics.Color

// Extension color used by NotesScreen
val NoteColor.swatch: Color
    get() = when (this) {
        NoteColor.Peach -> Color(0xFFFFE1E8)
        NoteColor.Mint  -> Color(0xFFE8F6EF)
        NoteColor.Blue  -> Color(0xFFEFF3FD)
        NoteColor.Lav   -> Color(0xFFF0EAFE)
        NoteColor.Lemon -> Color(0xFFFFF3C5)
    }
