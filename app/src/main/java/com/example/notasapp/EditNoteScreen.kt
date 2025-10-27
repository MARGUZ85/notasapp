package com.example.notasapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notasapp.Data.NotaSQLite
import com.example.notasapp.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    navController: NavController,
    notaId: Int,
    viewModel: HomeViewModel = viewModel()
) {
    val nota = viewModel.getNotaById(notaId)
    var title by remember { mutableStateOf(nota?.titulo ?: "") }
    var description by remember { mutableStateOf(nota?.descripcion ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Nota") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.updateNota(NotaSQLite(id = notaId, titulo = title, descripcion = description))
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    viewModel.deleteNota(notaId)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar Nota")
            }
        }
    }
}
