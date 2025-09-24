// app/src/main/java/com/keagan/finalapp/data/Repo.kt
package com.keagan.finalapp.data

import com.keagan.finalapp.ui.theme.model.EventItem
import com.keagan.finalapp.ui.theme.model.NoteColor
import com.keagan.finalapp.ui.theme.model.StickyNote
import com.keagan.finalapp.ui.theme.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object Repo {

    // ---- Tasks ----
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun addTask(title: String, tag: String = "study"): Task {
        val t = Task(id = System.currentTimeMillis(), title = title, tag = tag, done = false)
        _tasks.value = listOf(t) + _tasks.value
        return t
    }

    fun toggleTask(id: Long) {
        _tasks.value = _tasks.value.map { if (it.id == id) it.copy(done = !it.done) else it }
    }

    fun deleteTask(id: Long) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    fun setTasks(list: List<Task>) { _tasks.value = list }


    // ---- Events ----
    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val events: StateFlow<List<EventItem>> = _events

    fun addEvent(time: String, title: String): EventItem {
        val e = EventItem(id = System.currentTimeMillis(), time = time, title = title)
        _events.value = _events.value + e
        return e
    }

    fun deleteEvent(id: Long) {
        _events.value = _events.value.filter { it.id != id }
    }

    fun setEvents(list: List<EventItem>) { _events.value = list }


    // ---- Notes ----
    private val _notes = MutableStateFlow<List<StickyNote>>(emptyList())
    val notes: StateFlow<List<StickyNote>> = _notes

    fun addNote(text: String, color: NoteColor): StickyNote {
        val n = StickyNote(id = System.currentTimeMillis(), text = text, color = color)
        _notes.value = listOf(n) + _notes.value
        return n
    }

    fun deleteNote(id: Long) {
        _notes.value = _notes.value.filter { it.id != id }
    }

    fun recolorNote(id: Long, color: NoteColor) {
        _notes.value = _notes.value.map { if (it.id == id) it.copy(color = color) else it }
    }

    fun setNotes(list: List<StickyNote>) { _notes.value = list }
}
