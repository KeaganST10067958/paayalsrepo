package com.keagan.finalapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private fun newId(): String = System.currentTimeMillis().toString()

class DataStoreRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasksFlow: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val eventsFlow: StateFlow<List<EventItem>> = _events.asStateFlow()

    private val _notes = MutableStateFlow<List<StickyNote>>(emptyList())
    val notesFlow: StateFlow<List<StickyNote>> = _notes.asStateFlow()

    fun getTasks() = tasksFlow
    fun getEvents() = eventsFlow
    fun getNotes() = notesFlow

    // ---- TASKS ----
    fun addTask(title: String, tag: String? = null, done: Boolean = false) {
        val task = Task(id = newId(), title = title, done = done, tag = tag)
        _tasks.value = _tasks.value + task
    }
    fun toggleTask(id: String) {
        _tasks.value = _tasks.value.map { if (it.id == id) it.copy(done = !it.done) else it }
    }
    fun deleteTask(id: String) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    // ---- EVENTS ----
    fun addEvent(title: String, start: Long, end: Long, type: String = "study") {
        val event = EventItem(id = newId(), title = title, start = start, end = end, type = type)
        _events.value = _events.value + event
    }

    // ---- NOTES ----
    fun addNote(title: String, text: String, color: Long) {
        val note = StickyNote(id = newId(), title = title, text = text, color = color)
        _notes.value = _notes.value + note
    }
    fun deleteNote(id: String) {
        _notes.value = _notes.value.filter { it.id != id }
    }
    fun recolorNote(id: String, color: Long) {
        _notes.value = _notes.value.map { if (it.id == id) it.copy(color = color) else it }
    }
    fun togglePin(id: String) {
        _notes.value = _notes.value.map { if (it.id == id) it.copy(pinned = !it.pinned) else it }
    }
}
