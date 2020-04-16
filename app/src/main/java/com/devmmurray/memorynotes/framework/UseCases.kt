package com.devmmurray.memorynotes.framework

import com.devmmurray.core.usecase.AddNote
import com.devmmurray.core.usecase.GetAllNotes
import com.devmmurray.core.usecase.GetNote
import com.devmmurray.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote
    )