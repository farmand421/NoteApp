package com.ramand.noteapp.ui.main

import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.data.rpository.main.MainRepository
import com.ramand.noteapp.utils.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: MainRepository , private val view:MainContracts.View)
    :MainContracts.Presenter,BasePresenterImpl(){
    override fun loadAllNotes() {
        disposable = repository.loadAllNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.isNotEmpty())(
                        view.showAllNotes(it)
                )else(
                        view.showEmpty()
                )
            }
    }

    override fun deleteNote(entity: NoteEntity) {
        disposable = repository.deleteNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                view.deleteMessage()
            }
    }

    override fun filterNote(priority: String) {
        disposable = repository.filterNote(priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.isNotEmpty())(
                        view.showAllNotes(it)
                        )else(
                        view.showEmpty()
                        )
            }
    }

    override fun searchNote(title: String) {
        disposable = repository.searchNote(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.isNotEmpty())(
                        view.showAllNotes(it)
                        )else(
                        view.showEmpty()
                        )
            }
    }
}