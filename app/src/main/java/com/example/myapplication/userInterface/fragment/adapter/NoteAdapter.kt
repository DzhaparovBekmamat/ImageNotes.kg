package com.example.myapplication.userInterface.fragment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.NoteModel
import com.example.myapplication.databinding.NoteListBinding

class NoteAdapter(private val click: Result) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val list = mutableListOf<NoteModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNote(noteList: List<NoteModel>) {
        list.clear()
        list.addAll(noteList)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val binding: NoteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val noteModel = list[position]
            noteModel.image?.let { binding.imageViewNoteList.setImageResource(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(position)
        holder.itemView.setOnLongClickListener {
            click.onLongClick(list[position])
            true
        }
    }

    interface Result {
        fun onLongClick(noteModel: NoteModel)
    }
}
