//Nombre de Archivo HomeViewModel.kt
package com.example.notasapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notasapp.Data.NotaSQLite
import com.example.notasapp.Data.NotasRepository
import com.example.notasapp.Data.NotasDatabaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NotasRepository(NotasDatabaseHelper(application))

    private val _notasList = MutableStateFlow<List<NotaSQLite>>(emptyList())
    val notasList: StateFlow<List<NotaSQLite>> = _notasList

    private val _mensajeConfirmacion = MutableStateFlow<String?>(null)
    val mensajeConfirmacion: StateFlow<String?> = _mensajeConfirmacion

    init {
        loadNotas()
    }

    fun loadNotas() {
        viewModelScope.launch {
            val notas = repository.getNotas()
            Log.d("HomeViewModel", "Notas cargadas: ${notas.size}")
            _notasList.value = notas
        }
    }

    fun addNota(titulo: String, descripcion: String) {
        viewModelScope.launch {
            repository.insertNota(titulo, descripcion)
            _mensajeConfirmacion.value = "Nota agregada"
            loadNotas()
        }
    }

    fun deleteNota(id: Int) {
        viewModelScope.launch {
            repository.deleteNota(id)
            _mensajeConfirmacion.value = "Nota eliminada"
            loadNotas()
        }
    }

    fun updateNota(nota: NotaSQLite) {
        viewModelScope.launch {
            val existente = repository.getNotaById(nota.id)
            if (existente != null) {
                repository.updateNota(nota)
                _mensajeConfirmacion.value = "Nota actualizada"
                loadNotas()
            } else {
                Log.w("HomeViewModel", "Nota con ID ${nota.id} no encontrada. No se actualiza.")
                _mensajeConfirmacion.value = "Error: nota no encontrada"
            }
        }
    }

    fun getNotaById(id: Int): NotaSQLite? {
        val nota = repository.getNotaById(id)
        Log.d("HomeViewModel", "Buscando nota por ID: $id → $nota")
        return nota
    }

    fun clearMensaje() {
        _mensajeConfirmacion.value = null
    }
}
