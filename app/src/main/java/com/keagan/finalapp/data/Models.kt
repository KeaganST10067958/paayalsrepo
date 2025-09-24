package com.keagan.finalapp.data

data class Task(
    val id: String,
    val title: String,
    val done: Boolean = false,
    val tag: String? = null // "study", "personal", "urgent" (for UI chips)
)

data class EventItem(
    val id: String,
    val title: String,
    val start: Long, // epoch millis
    val end: Long,
    val type: String = "study" // color code in UI
)

data class StickyNote(
    val id: String,
    val title: String,
    val text: String,
    val color: Long = 0xFFFFF59DL, // <- Long literal, soft yellow
    val pinned: Boolean = false,
    val created: Long = System.currentTimeMillis()
)
