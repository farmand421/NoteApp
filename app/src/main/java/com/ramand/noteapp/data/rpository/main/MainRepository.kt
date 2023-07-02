package com.ramand.noteapp.data.rpository.main

import com.ramand.noteapp.data.database.NoteDao
import com.ramand.noteapp.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {

    fun loadAllNotes() = dao.getAllNote()
    fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
    fun filterNote(priority: String) = dao.filterNote(priority)
    fun searchNote(title: String) = dao.searchNote(title)

}