package com.example.notestest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notestest.ui.theme.NotesTestTheme
import com.example.notestest.ui.theme.note.NoteScreen
import com.example.notestest.ui.theme.note.NoteViewModel
import com.example.notestest.ui.theme.note_list.NoteListScreen
import com.example.notestest.ui.theme.note_list.NoteListViewModel
import com.example.notestest.utils.Route
import com.example.notestest.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.noteList
                ) {
                    composable(route = Route.noteList) {
                        val viewModel = hiltViewModel<NoteListViewModel>()
                        val noteList by viewModel.noteList.collectAsStateWithLifecycle()

                        NoteListScreen(
                            noteList = noteList,
                            onNoteClick = {
                            navController.navigate(
                                Route.note.replace(
                                    "{id}",
                                    it.id.toString()
                                )
                            ) },
                            onAddNoteClick = {
                                navController.navigate(Route.note)
                            }
                        )
                    }

                    composable(Route.note) {
                        val viewModel = hiltViewModel<NoteViewModel>()
                        val noteState by viewModel.noteState.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = true) {
                            viewModel.event.collect { event->
                                when(event) {
                                    is UiEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }
                                    else -> Unit
                                }
                            }
                        }

                        NoteScreen(
                            state = noteState,
                            onEvent = viewModel::onEvent)

                    }
                }
            }
        }
    }
}

