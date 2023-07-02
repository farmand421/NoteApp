package com.ramand.noteapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.utils.NOTE_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(entity: NoteEntity): Completable

    @Delete
    fun deleteNote(entity: NoteEntity): Completable

    @Update
    fun updateNote(entity: NoteEntity): Completable

    @Query("SELECT * FROM $NOTE_TABLE ")
    fun getAllNote(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id ==:id")
    fun getNote(id: Int): Observable<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE priority == :priority")
    fun filterNote(priority: String): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE title LIKE '%'|| :title || '%'")
    fun searchNote(title: String): Observable<List<NoteEntity>>
}