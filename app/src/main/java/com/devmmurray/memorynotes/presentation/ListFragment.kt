package com.devmmurray.memorynotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.memorynotes.R
import com.devmmurray.memorynotes.framework.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), ListAction {

    private val noteListAdapter = NoteListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }
        addNote.setOnClickListener { goToNoteDetails() }
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(notesListView).navigate(action)
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner, Observer { notesList ->
            loadingView.visibility = View.GONE
            notesListView.visibility = View.VISIBLE
            noteListAdapter.updateNotes(notesList.sortedByDescending { it.updateTime })
        })

    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }
}
