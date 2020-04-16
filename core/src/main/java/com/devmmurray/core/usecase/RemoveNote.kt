package com.devmmurray.core.usecase

import com.devmmurray.core.data.Note
import com.devmmurray.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}