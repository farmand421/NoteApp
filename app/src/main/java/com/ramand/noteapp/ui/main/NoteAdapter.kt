package com.ramand.noteapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ramand.noteapp.R
import com.ramand.noteapp.data.model.NoteEntity
import com.ramand.noteapp.databinding.ItemNotesBinding
import com.ramand.noteapp.utils.*
import javax.inject.Inject

class NoteAdapter @Inject constructor() : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private lateinit var binding: ItemNotesBinding
    private lateinit var context: Context
    private var moviesList = emptyList<NoteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //GetItemFromPagingDataAdapter
        holder.bind(moviesList[position])
        //NotDuplicateItem
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("setTxtI18n")
        fun bind(item: NoteEntity) {
            binding.apply {
                titleTxt.text = item.title
                descTxt.text = item.desc
                //priority
                when (item.priority) {
                    HIGH -> pirorityColor.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.red))
                    NORMAL -> pirorityColor.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.yellow))
                    LOW -> pirorityColor.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.aqua))
                }
                //Categories
                when (item.category) {
                    HOME -> categoryImg.setImageResource(R.drawable.home)
                    WORK -> categoryImg.setImageResource(R.drawable.work)
                    EDUCATION -> categoryImg.setImageResource(R.drawable.education)
                    HEALTH -> categoryImg.setImageResource(R.drawable.healthcare)
                }
                //Menu
                menuImg.setOnClickListener {
                    val popupMenu = androidx.appcompat.widget.PopupMenu(context, it)
                    popupMenu.menuInflater.inflate(R.menu.menu_items, popupMenu.menu)
                    popupMenu.show()
                    //Click
                    popupMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.itemEdit -> {
                                onItemSelectedListener?.let {
                                    it(item, EDIT)
                                }
                            }
                            R.id.itemDelete -> {
                                onItemSelectedListener?.let {
                                    it(item, DELETE)
                                }
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                }


            }
        }
    }

    private var onItemSelectedListener: ((NoteEntity, String) -> Unit)? = null
    fun setOnItemClickListener(listener: (NoteEntity, String) -> Unit) {
        onItemSelectedListener = listener
    }

    fun setData(data: List<NoteEntity>) {
        val moviesDiffUtil = NotesDiffutils(moviesList, data)
        val diffutils = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = data
        diffutils.dispatchUpdatesTo(this)


    }

    class NotesDiffutils(
        private val oldItem: List<NoteEntity>,
        private val newItem: List<NoteEntity>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

    }
}
