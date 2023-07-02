package com.ramand.noteapp.data.rpository.add

import com.ramand.noteapp.data.database.NoteDao
import com.ramand.noteapp.data.model.NoteEntity
import javax.inject.Inject

class AddNoteRepository @Inject constructor(private val dao: NoteDao) {

    fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
    fun detailNote(id: Int) = dao.getNote(id)
    fun updateNote(entity: NoteEntity) = dao.updateNote(entity)
}