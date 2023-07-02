package com.ramand.noteapp.ui.main

import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.utils.base.BasePresenter

interface MainContracts {
    interface View{
        fun showAllNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun deleteMessage()
    }

    interface Presenter: BasePresenter{
        fun loadAllNotes()
        fun deleteNote(entity: NoteEntity)
        fun filterNote(priority: String)
        fun searchNote(title: String)
    }
}