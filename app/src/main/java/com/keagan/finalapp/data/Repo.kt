package com.keagan.finalapp.data

class Repo(
    private val store: DataStoreRepository = DataStoreRepository()
) {
    // expose flows
    val tasks = store.getTasks()      // StateFlow<List<Task>>
    val notes = store.getNotes()      // StateFlow<List<StickyNote>>
    val events = store.getEvents()    // StateFlow<List<EventItem>>

    // tasks
    fun addTask(title: String) = store.addTask(title)
    fun toggleTask(id: String) = store.toggleTask(id)
    fun deleteTask(id: String) = store.deleteTask(id)

    // notes
    fun addNote(
        title: String,
        text: String,
        color: Long = DEFAULT_NOTE_COLOR
    ) = store.addNote(title, text, color)
    fun deleteNote(id: String) = store.deleteNote(id)
    fun recolorNote(id: String, color: Long) = store.recolorNote(id, color)

    // events
    fun addEvent(title: String, start: Long, end: Long) =
        store.addEvent(title, start, end)

    companion object {
        // light yellow
        const val DEFAULT_NOTE_COLOR: Long = 0xFFFFF59DL
    }
}
