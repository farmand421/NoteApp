package com.ramand.noteapp.ui.add

import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.utils.base.BasePresenter

interface NoteContracts {

    interface View{
        fun close()
        fun loadNote(note: NoteEntity)
    }

    interface Presenter: BasePresenter{
        fun saveNote(entity: NoteEntity)
        fun detailNote(id: Int)
        fun updateNote(entity: NoteEntity)
    }

}