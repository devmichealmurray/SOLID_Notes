package com.devmmurray.core.usecase

import com.devmmurray.core.data.Note
import com.devmmurray.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}