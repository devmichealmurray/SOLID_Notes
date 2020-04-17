package com.devmmurray.memorynotes.framework.di

import com.devmmurray.memorynotes.framework.ListViewModel
import com.devmmurray.memorynotes.framework.NoteViewModel
import dagger.Component

@Component(
    modules = [ApplicationModule::class,
        RepositoryModule::class,
        UseCasesModule::class]
)
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}