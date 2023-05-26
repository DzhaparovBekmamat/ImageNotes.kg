package com.example.myapplication.userInterface.fragment.addNote

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.model.NoteModel
import com.example.myapplication.databinding.FragmentAddNoteBinding
import com.example.myapplication.userInterface.fragment.noteAdapter.NoteAdapter
import com.example.myapplication.userInterface.sharedPreferences.app.App

@RequiresApi(Build.VERSION_CODES.R)
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(FragmentAddNoteBinding::inflate) {
    private val adapter: NoteAdapter by lazy { NoteAdapter(this::listener, this::share) }

    override fun setUpUI() {
        val model = App.database.getDao().getAllNotes()
        adapter.addNote(model as ArrayList<NoteModel>)
        binding.recyclerView.adapter = adapter
    }

    override fun setUpObserver() {
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.noteFragment)
        }
    }


    private fun listener(model: NoteModel) {
        App.database.getDao().deleteNote(model)
        val model = App.database.getDao().getAllNotes()
        adapter.addNote(model as ArrayList<NoteModel>)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun share(pos: Int) {
        val note = App.database.getDao().getAllNotes()
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT, "${note[pos].title} \n ${note[pos].description} \n ${note[pos].date}"
        )
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Поделиться заметкой"))
    }
}










