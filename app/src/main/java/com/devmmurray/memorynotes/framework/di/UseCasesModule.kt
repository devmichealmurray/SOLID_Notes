package com.devmmurray.memorynotes.framework.di

import com.devmmurray.core.repository.NoteRepository
import com.devmmurray.core.usecase.*
import com.devmmurray.memorynotes.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repository: NoteRepository) =
        UseCases(
            AddNote(repository),
            GetAllNotes(repository),
            GetNote(repository),
            RemoveNote(repository),
            GetWordCount()
        )
}