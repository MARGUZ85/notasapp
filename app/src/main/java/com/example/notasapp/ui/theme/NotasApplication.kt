package com.example.notasapp

import android.app.Application

class NotasApplication : Application() {
    val repository: NotasRepository by lazy {
        NotasRepository()
    }
}
