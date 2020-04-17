package com.devmmurray.memorynotes.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmmurray.core.data.Note
import com.devmmurray.memorynotes.framework.di.ApplicationModule
import com.devmmurray.memorynotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val notes = MutableLiveData<List<Note>>()

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }

    fun getNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()
            noteList.forEach { it.wordCount = useCases.getWordCount.invoke(it) }
            notes.postValue(noteList)
        }
    }
}