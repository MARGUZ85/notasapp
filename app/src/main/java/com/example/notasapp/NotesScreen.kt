package com.example.notasapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.notasapp.Data.NotaSQLite
import com.example.notasapp.ui.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val notas by viewModel.notasList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Notas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("newNoteScreen") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar nota")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(notas) { nota ->
                NoteCard(nota, onClick = {
                    navController.navigate("editNoteScreen/${nota.id}")
                })
            }
        }
    }
}

@Composable
fun NoteCard(nota: NotaSQLite, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(nota.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                nota.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
