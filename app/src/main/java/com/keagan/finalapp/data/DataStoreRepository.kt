package com.keagan.finalapp.ui.theme.data

import com.keagan.finalapp.ui.theme.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Simple in-memory repository. Replace with real DataStore later.
 */
object DataStoreRepository {

    // ---------- Tasks ----------
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun setTasks(list: List<Task>) { _tasks.value = list }

    fun addTask(title: String, tag: String = "study"): Task {
        val t = Task(id = System.currentTimeMillis(), title = title, tag = tag, done = false)
        _tasks.value = listOf(t) + _tasks.value
        return t
    }

    fun toggleTask(id: Long) {
        _tasks.value = _tasks.value.map { if (it.id == id) it.copy(done = !it.done) else it }
    }

    fun removeTask(id: Long) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    // ---------- Events / Agenda ----------
    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val events: StateFlow<List<EventItem>> = _events

    fun setEvents(list: List<EventItem>) { _events.value = list }

    fun addEvent(time: String, title: String): EventItem {
        val e = EventItem(id = System.currentTimeMillis(), time = time, title = title)
        _events.value = _events.value + e
        return e
    }

    fun removeEvent(id: Long) {
        _events.value = _events.value.filter { it.id != id }
    }

    // ---------- Notes ----------
    private val _notes = MutableStateFlow<List<StickyNote>>(emptyList())
    val notes: StateFlow<List<StickyNote>> = _notes

    fun setNotes(list: List<StickyNote>) { _notes.value = list }

    fun addNote(text: String, color: NoteColor): StickyNote {
        val n = StickyNote(id = System.currentTimeMillis(), text = text, color = color)
        _notes.value = listOf(n) + _notes.value
        return n
    }

    fun removeNote(id: Long) {
        _notes.value = _notes.value.filter { it.id != id }
    }
}
