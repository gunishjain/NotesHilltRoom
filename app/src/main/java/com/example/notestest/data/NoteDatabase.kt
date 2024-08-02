package com.example.notestest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notestest.data.dao.NoteDao
import com.example.notestest.data.local.models.NoteEntity


@Database(
    version = 1,
    entities = [NoteEntity::class]
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val dao : NoteDao

    companion object {
        const val name = "note_db"

    }

}