package com.devmmurray.memorynotes.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.devmmurray.core.data.Note
import com.devmmurray.memorynotes.R
import com.devmmurray.memorynotes.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).NoteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        checkButton.setOnClickListener {
            if (titleView.text.toString() != "" || contentView.text.toString() != "") {
                val time = System.currentTimeMillis()
                currentNote.title = titleView.text.toString()
                currentNote.content = contentView.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(context!!)
                        .setTitle("Delete Note")
                        .setMessage("This will permanently delete your entry. \nAre Your Sure?")
                        .setPositiveButton("Delete") { dialogInterface, i ->
                            viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("Cancel") { dialogInterface, i -> }
                        .create()
                        .show()
                }
            }
        }
        return true
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast
                    .makeText(context, " Note Saved! ", Toast.LENGTH_SHORT)
                    .show()
                hideKeyboard()
                Navigation.findNavController(titleView).popBackStack()
            } else {
                Toast
                    .makeText(context, " Error Saving Note! ", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                currentNote = it
                titleView.setText(it.title)
                contentView.setText(it.content) }
        })
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleView.windowToken, 0)
    }
}
