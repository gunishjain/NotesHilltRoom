package com.example.notestest.data.repository

import com.example.notestest.data.dao.NoteDao
import com.example.notestest.data.local.models.Note
import com.example.notestest.data.mapper.asExternalModel
import com.example.notestest.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val dao: NoteDao
) {

    fun getAllNotes() : Flow<List<Note>>{
        return dao.getAllNotes().map {
            notes->
            notes.map {
                it.asExternalModel()
            }
        }

    }

    suspend fun getNoteById(id: Int) : Note? {
        return dao.getNoteById(id)?.asExternalModel()
    }

    suspend fun insertNote(note: Note) {
        dao.insertNote(note.toEntity())
    }

    suspend fun deleteNote(note: Note) {
            dao.deleteNote(note.toEntity())
    }

    suspend fun updateNote(note: Note) {
        dao.updateNote(note.toEntity())
    }


}