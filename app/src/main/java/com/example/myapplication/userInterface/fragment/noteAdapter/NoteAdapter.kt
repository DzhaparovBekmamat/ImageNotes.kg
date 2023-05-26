package com.example.myapplication.userInterface.fragment.noteAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.NoteModel
import com.example.myapplication.databinding.NoteListBinding

/**
 * Author: Dzhaparov Bekmamat
 */
class NoteAdapter(
    private val listener: (model: NoteModel) -> Unit, private val share: (pos: Int) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val list: MutableList<NoteModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addNote(noteList: List<NoteModel>) {
        this.list.clear()
        this.list.addAll(noteList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(val binding: NoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val noteModel = list[position]
            noteModel.title?.let { binding.titleNoteList.text = it }
            noteModel.description?.let { binding.descriptionNoteList.text = it }
            noteModel.date?.let { binding.dateNoteList.text = it }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(position)
        holder.binding.buttonRemove.setOnClickListener {
            listener(list[position])
        }

        holder.binding.buttonShare.setOnClickListener {
            share(position)
        }
    }

    interface IOnItem {
        fun delete(position: Int)
        fun share(position: Int)
    }
}