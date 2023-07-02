package com.ramand.noteapp.ui.main

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ramand.noteapp.R
import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.data.rpository.main.MainRepository
import com.ramand.noteapp.databinding.ActivityMainBinding
import com.ramand.noteapp.ui.add.NoteFragment
import com.ramand.noteapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , MainContracts.View {
    //Binding
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var presenter: MainPresenter

    //Other
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {
            //SetActionView
            setSupportActionBar(notesToolbar)
            //NoteDetail
            addNoteBtn.setOnClickListener { NoteFragment().show(supportFragmentManager,NoteFragment().tag) }
            //Load all notes
            presenter.loadAllNotes()
            //AdapterClick
            noteAdapter.setOnItemClickListener { Entity, state ->
                when(state){
                    EDIT -> {
                        val noteFragment = NoteFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID,Entity.id)
                        noteFragment.arguments = bundle
                        noteFragment.show(supportFragmentManager,NoteFragment().tag)
                    }
                    DELETE -> {
                        val noteEntity = NoteEntity(Entity.id,Entity.title,Entity.desc,Entity.category,Entity.priority)
                        presenter.deleteNote(noteEntity)
                    }
                }
            }
            //Filter
            notesToolbar.setOnMenuItemClickListener {
                when(it.itemId){
                R.id.actionFilter -> {
                    filterByPriority()
                    return@setOnMenuItemClickListener true
                }else -> {
                 return@setOnMenuItemClickListener false
                }
                }
            }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar,menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.Search)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchNote(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showAllNotes(notes: List<NoteEntity>) {
        binding.emptyList.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE

        noteAdapter.setData(notes)
        binding.noteList.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }

    }

    override fun showEmpty() {
        binding.emptyList.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }

    override fun deleteMessage() {
        Snackbar.make(binding.root,DELETE_MESSAGE,Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun filterByPriority(){
        val builder = AlertDialog.Builder(this)
        val priories = arrayOf(ALL,HIGH, NORMAL, LOW)
        builder.setSingleChoiceItems(priories,selectedItem){ dialog,item ->
            when(item){
                0 -> {
                    presenter.loadAllNotes()
                }
                in 1..3 ->{
                    presenter.filterNote(priories[item])
                }
            }
            selectedItem = item
            dialog.dismiss()

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}