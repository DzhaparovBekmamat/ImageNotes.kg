package com.example.myapplication.userInterface.fragment.addNote

import android.app.AlertDialog
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
        binding.buttonSort.setOnClickListener {
            showSortDialog()
        }
    }

    private fun showSortDialog() {
        val sortOptions = arrayOf("По алфавиту", "По дате")
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Выберите сортировку")
            .setItems(sortOptions) { _, which ->
                when (which) {
                    0 -> sortNotesAlphabetically()
                    1 -> sortNotesByDate()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
        dialog.show()
    }

    private fun sortNotesAlphabetically() {
        val sortedNotes = adapter.getNoteList().sortedBy { it.title }
        adapter.addNote(sortedNotes)
    }

    private fun sortNotesByDate() {
        val sortedNotes = adapter.getNoteList().sortedByDescending { it.date }
        adapter.addNote(sortedNotes)
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
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Удаление заметки")
        alertDialog.setMessage("Вы уверены, что хотите удалить эту заметку?")
        alertDialog.setPositiveButton("Да") { _, _ ->
            App.database.getDao().deleteNote(model)
            val notes = App.database.getDao().getAllNotes() as ArrayList<NoteModel>
            adapter.addNote(notes)
        }
        alertDialog.setNegativeButton("Нет") { _, _ ->
        }
        alertDialog.show()
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










