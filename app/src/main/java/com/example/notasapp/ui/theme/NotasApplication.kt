//Nombre de Archivo NotasApplication.kt
package com.example.notasapp

import android.app.Application
import com.example.notasapp.Data.NotasRepository
import com.example.notasapp.Data.NotasDatabaseHelper

class NotasApplication : Application() {
    val repository: NotasRepository by lazy {
        val dbHelper = NotasDatabaseHelper(this)
        NotasRepository(dbHelper)
    }
}
