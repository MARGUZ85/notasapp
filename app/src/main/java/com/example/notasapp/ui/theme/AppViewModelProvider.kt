package com.example.notasapp

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Proveedor de ViewModels para la aplicación NotasApp.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this.asNotasApplication()
            HomeViewModel(application.repository)
        }
    }
}

fun CreationExtras.asNotasApplication(): NotasApplication {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NotasApplication)
}
