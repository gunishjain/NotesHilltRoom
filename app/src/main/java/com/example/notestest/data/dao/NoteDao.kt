package com.example.notestest.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notestest.data.local.models.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM  NoteEntity")
    fun getAllNotes() : Flow<List<NoteEntity>>

    @Query("""
        SELECT * FROM NoteEntity
        where id = :id
    """)
    suspend fun getNoteById(id: Int) : NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)




}