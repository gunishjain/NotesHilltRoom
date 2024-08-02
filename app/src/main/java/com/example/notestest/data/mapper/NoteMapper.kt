package com.example.notestest.data.mapper

import com.example.notestest.data.local.models.Note
import com.example.notestest.data.local.models.NoteEntity

fun NoteEntity.asExternalModel(): Note = Note(
    id, title, note
)

fun Note.toEntity(): NoteEntity = NoteEntity(
    id, title, content
)