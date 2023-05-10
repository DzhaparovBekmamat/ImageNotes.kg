package com.example.myapplication.userInterface.fragment.addNote

import androidx.navigation.fragment.findNavController
import com.example.myapplication.dataBase.app.App
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.data.model.NoteModel
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.userInterface.fragment.adapter.ImageAdapter

class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {
    private val adapter: ImageAdapter by lazy { ImageAdapter(this::onClick) }
    private val imageList = mutableListOf<Int>()

    override fun setUpUI() {
        imageList.clear()
        imageList.add(R.drawable.java)
        imageList.add(R.drawable.kotlin)
        imageList.add(R.drawable.python)
        adapter.setList(imageList)
        binding.recyclerView.adapter = adapter
    }

    private fun onClick(position: Int) {
        val noteModel = NoteModel(image = position)
        App.database.getDao().setNote(noteModel)
    }

    override fun setUpObserver() {
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }
    }
}
