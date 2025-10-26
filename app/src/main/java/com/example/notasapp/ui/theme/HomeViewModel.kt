package com.example.notasapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: NotasRepository) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        loadNotes()
    }

    private fun loadNotes() {
        // Simulación de datos iniciales
        _homeUiState.value = HomeUiState(
            itemList = listOf(
                NoteItem(1, "Lista del súper", "Comprar leche, pan y huevos."),
                NoteItem(2, "Cita médica", "Dentista a las 4:00 PM."),
                NoteItem(3, "Proyecto Android", "Avanzar con la pantalla de notas.")
            )
        )
    }
}

data class HomeUiState(
    val itemList: List<NoteItem> = emptyList()
)
