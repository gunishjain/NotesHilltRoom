package com.example.notestest.ui.theme.note

sealed interface NoteEvent {

    data class TitleChanged(val value: String) : NoteEvent
    data class ContentChanged(val value: String): NoteEvent
    object Save: NoteEvent
    object NavigateBack : NoteEvent
    object DeleteNote: NoteEvent
}