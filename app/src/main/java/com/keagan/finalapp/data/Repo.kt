package com.keagan.finalapp.data

class Repo(private val store: DataStoreRepository = DataStoreRepository()) {
    val tasks = store.getTasks()
    val notes = store.getNotes()
    val events = store.getEvents()

    // tasks
    fun addTask(title: String, tag: String? = null) = store.addTask(title, tag)
    fun toggleTask(id: String) = store.toggleTask(id)
    fun deleteTask(id: String) = store.deleteTask(id)

    // notes
    fun addNote(title: String, text: String, color: Long = DEFAULT_NOTE_COLOR) = store.addNote(title, text, color)
    fun deleteNote(id: String) = store.deleteNote(id)
    fun recolorNote(id: String, color: Long) = store.recolorNote(id, color)
    fun togglePin(id: String) = store.togglePin(id)

    // events
    fun addEvent(title: String, start: Long, end: Long, type: String = "study") = store.addEvent(title, start, end, type)

    companion object { const val DEFAULT_NOTE_COLOR: Long = 0xFFFFF59DL }
}
