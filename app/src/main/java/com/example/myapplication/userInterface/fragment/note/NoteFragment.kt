package com.example.myapplication.userInterface.fragment.note

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.model.NoteModel
import com.example.myapplication.databinding.FragmentNoteBinding
import com.example.myapplication.userInterface.fragment.adapter.NoteAdapter
import com.example.myapplication.userInterface.sharedPreferences.app.Application

class NoteFragment : BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate),
    NoteAdapter.Result {

    private val adapter: NoteAdapter by lazy { NoteAdapter(this) }

    override fun setUpUI() {
        setupRecyclerView()
        loadNotes()
        binding.buttonFragmentNote.setOnClickListener {
            findNavController().navigate(R.id.addNoteFragment)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        Application.database.getDao().deleteNote(noteModel)
        Toast.makeText(requireContext(), "Фотография удалена", Toast.LENGTH_SHORT).show()
        loadNotes()
    }

    private fun setupRecyclerView() {
        binding.rvResult.adapter = adapter
    }

    private fun loadNotes() {
        val noteList = Application.database.getDao().getAllNotes()
        adapter.setNote(noteList)
    }
}
