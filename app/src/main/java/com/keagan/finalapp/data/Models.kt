package com.keagan.finalapp.data

data class Task(
    val id: String,
    val title: String,
    val done: Boolean = false
)

data class EventItem(
    val id: String,
    val title: String,
    val start: Long,
    val end: Long
)

data class StickyNote(
    val id: String,
    val title: String,
    val text: String,
    val color: Long = 0xFFFFF59D, // soft yellow
    val pinned: Boolean = false,
    val created: Long = System.currentTimeMillis()
)
