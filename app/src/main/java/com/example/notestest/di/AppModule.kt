package com.example.notestest.di

import android.content.Context
import androidx.room.Room
import com.example.notestest.data.NoteDatabase
import com.example.notestest.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.name
        ).build()


    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase) : NoteRepository =
        NoteRepository(database.dao)

}