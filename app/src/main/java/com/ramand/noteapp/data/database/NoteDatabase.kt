package com.ramand.noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramand.noteapp.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun notDao(): NoteDao
}