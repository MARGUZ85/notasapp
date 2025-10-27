//Nombre de Archivo IDatabaseHelper.kt
package com.example.notasapp.Data
import com.example.notasapp.Data.NotaSQLite

interface IDatabaseHelper {
    fun insertNota(titulo: String, descripcion: String)
    fun getAllNotas(): List<NotaSQLite>
    fun getNotaById(id: Int): NotaSQLite?
    fun updateNota(id: Int, titulo: String, descripcion: String)
    fun deleteNota(id: Int)
}
