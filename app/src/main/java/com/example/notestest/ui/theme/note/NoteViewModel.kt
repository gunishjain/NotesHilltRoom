package com.example.notestest.ui.theme.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notestest.data.local.models.Note
import com.example.notestest.data.repository.NoteRepository
import com.example.notestest.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<String>("id")?.let {
            val id = it.toInt()
            viewModelScope.launch {
                repository.getNoteById(id)?.let { note ->
                    _noteState.update { screenState->
                        screenState.copy(
                            id=note.id,
                            title = note.title,
                            content = note.content
                        )
                    }
                }
            }
        }

    }

    private val _noteState = MutableStateFlow(NoteState())
    val noteState =_noteState.asStateFlow()

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    fun onEvent(event: NoteEvent) {
        when(event) {
            is NoteEvent.ContentChanged -> {
                _noteState.update {
                    it.copy(
                        content = event.value
                    )
                }
            }

            NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    val state = noteState.value
                    val note = Note(
                        id= state.id,
                        title = state.title,
                        content = state.content
                    )
                    repository.deleteNote(note)
                }

                sendEvent(UiEvent.NavigateBack)
            }
            NoteEvent.NavigateBack -> sendEvent(UiEvent.NavigateBack)
            NoteEvent.Save -> {
                viewModelScope.launch {
                    val state = noteState.value
                    val note = Note(
                        id = state.id,
                        title = state.title,
                        content = state.content
                    )
                    if(state.id==null){
                        repository.insertNote(note)
                    } else {
                        repository.updateNote(note)
                    }
                    sendEvent(UiEvent.NavigateBack)

                }
            }
            is NoteEvent.TitleChanged -> {
                viewModelScope.launch {
                    _noteState.update {
                        it.copy(
                            title = event.value
                        )
                    }
                }
            }
        }
    }

}