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
            Log.d("HomeViewModel", "Agregando nota: $titulo - $descripcion")
            repository.insertNota(titulo, descripcion)
            loadNotas()
        }
    }

    fun deleteNota(id: Int) {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Eliminando nota con ID: $id")
            repository.deleteNota(id)
            loadNotas()
        }
    }

    fun updateNota(nota: NotaSQLite) {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Actualizando nota: $nota")
            val existente = repository.getNotaById(nota.id)
            if (existente != null) {
                repository.updateNota(nota)
                loadNotas()
            } else {
                Log.w("HomeViewModel", "Nota con ID ${nota.id} no encontrada. No se actualiza.")
            }
        }
    }

    fun getNotaById(id: Int): NotaSQLite? {
        val nota = repository.getNotaById(id)
        Log.d("HomeViewModel", "Buscando nota por ID: $id → $nota")
        return nota
    }
}
