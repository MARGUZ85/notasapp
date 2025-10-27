package com.example.notasapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notasapp.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Nueva Nota") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.addNota(title, description)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar Nota") }
        }
    }
}
