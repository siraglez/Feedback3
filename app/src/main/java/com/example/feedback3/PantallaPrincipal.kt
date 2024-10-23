package com.example.feedback3

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PantallaPrincipal(
    novelas: List<Novela>,
    onAgregarClick: () -> Unit,
    onEliminarClick: (Novela) -> Unit,
    onVerDetallesClick: (Novela) -> Unit,
    isLoading: Boolean
) {
    val novelaViewModel: NovelaViewModel = viewModel()
    var novelas by remember { mutableStateOf<List<Novela>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    //Cargar novelas al iniciar el composable
    if (isLoading) {
        novelaViewModel.cargarNovelasDesdeFirebase { loadedNovelas ->
            novelas = loadedNovelas
            isLoading = false
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Novela")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            // Mostrar un indicador de carga si está en proceso de carga
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                // Si las novelas no están vacías, muestra la lista
                if (novelas.isNotEmpty()) {
                    LazyColumn {
                        items(novelas) { novela ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onVerDetallesClick(novela) }
                                    .padding(8.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = novela.titulo, style = MaterialTheme.typography.titleLarge)
                                    Text(text = "Autor: ${novela.autor}")
                                }
                                if (novela.esFavorita) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Favorita",
                                        tint = Color.Yellow
                                    )
                                }
                                IconButton(onClick = { onEliminarClick(novela) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar Novela")
                                }
                            }
                        }
                    }
                } else {
                    // Si no hay novelas, mostrar un mensaje alternativo
                    Text("No hay novelas disponibles.", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    PantallaPrincipal(
        novelas = listOf(
            Novela("Novela 1", "Autor 1", 2022, "Sinopsis 1"),
            Novela("Novela 2", "Autor 2", 2021, "Sinopsis 2", esFavorita = true)
        ),
        onAgregarClick = {},
        onEliminarClick = {},
        onVerDetallesClick = {},
        isLoading = false // Aquí puedes ajustar el estado para la vista previa
    )
}