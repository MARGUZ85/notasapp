//Nombre de Archivo NotasRepository.kt
package com.example.notasapp.Data
import com.example.notasapp.Data.NotaSQLite

class NotasRepository(private val dbHelper: IDatabaseHelper) {

    fun getNotas(): List<NotaSQLite> = dbHelper.getAllNotas()

    fun insertNota(titulo: String, descripcion: String) {
        dbHelper.insertNota(titulo, descripcion)
    }

    fun deleteNota(id: Int) {
        dbHelper.deleteNota(id)
    }

    fun updateNota(id: Int, titulo: String, descripcion: String) {
        dbHelper.updateNota(id, titulo, descripcion)
    }

    fun updateNota(nota: NotaSQLite) {
        dbHelper.updateNota(nota.id, nota.titulo, nota.descripcion)
    }

    fun getNotaById(id: Int): NotaSQLite? = dbHelper.getNotaById(id)
}
