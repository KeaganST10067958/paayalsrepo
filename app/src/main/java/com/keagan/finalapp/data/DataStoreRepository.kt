package com.keagan.finalapp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private fun newId(): String = System.currentTimeMillis().toString()

class DataStoreRepository {

    // flows renamed earlier to avoid getter clash
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasksFlow: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _events = MutableStateFlow<List<EventItem>>(emptyList())
    val eventsFlow: StateFlow<List<EventItem>> = _events.asStateFlow()

    private val _notes = MutableStateFlow<List<StickyNote>>(emptyList())
    val notesFlow: StateFlow<List<StickyNote>> = _notes.asStateFlow()

    // Convenience getters (return the same flows)
    fun getTasks(): StateFlow<List<Task>> = tasksFlow
    fun getEvents(): StateFlow<List<EventItem>> = eventsFlow
    fun getNotes(): StateFlow<List<StickyNote>> = notesFlow

    // ---- TASKS ----
    fun addTask(title: String) {
        val task = Task(
            id = newId(),       // <-- String id
            title = title,
            done = false
        )
        _tasks.value = _tasks.value + task
    }

    fun toggleTask(id: String) {       // <-- String param
        _tasks.value = _tasks.value.map { t ->
            if (t.id == id) t.copy(done = !t.done) else t
        }
    }

    fun deleteTask(id: String) {       // <-- String param
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    // ---- EVENTS ----
    fun addEvent(title: String, start: Long, end: Long) {
        val event = EventItem(
            id = newId(),       // <-- String id
            title = title,
            start = start,
            end = end
        )
        _events.value = _events.value + event
    }

    // ---- NOTES ----
    fun addNote(title: String, text: String, color: Long) {
        val note = StickyNote(
            id = newId(),       // <-- String id
            title = title,
            text = text,
            color = color
        )
        _notes.value = _notes.value + note
    }

    fun deleteNote(id: String) {       // <-- String param
        _notes.value = _notes.value.filter { it.id != id }
    }

    fun recolorNote(id: String, color: Long) {  // <-- String param
        _notes.value = _notes.value.map { n ->
            if (n.id == id) n.copy(color = color) else n
        }
    }
}
