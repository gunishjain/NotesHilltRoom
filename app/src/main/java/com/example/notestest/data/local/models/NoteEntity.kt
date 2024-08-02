package com.example.notestest.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int?,
    val title: String,
    val note: String,

)

