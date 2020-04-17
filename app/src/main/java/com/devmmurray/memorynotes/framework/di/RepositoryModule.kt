package com.devmmurray.memorynotes.framework.di

import android.app.Application
import com.devmmurray.core.repository.NoteRepository
import com.devmmurray.memorynotes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesRepository(app: Application) =
        NoteRepository(RoomNoteDataSource(app))
}