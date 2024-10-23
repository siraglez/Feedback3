package com.example.feedback3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetallesNovela(novela: Novela, onMarcarFavorita: (Novela) -> Unit, onVolver: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = novela.titulo, style = MaterialTheme.typography.titleLarge)
        Text(text = "Autor: ${novela.autor}")
        Text(text = "Año: ${novela.anioPublicacion}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = novela.sinopsis)
        Spacer(modifier = Modifier.height(16.dp))

        //Botón para marcar/desmarcar como favorita
        Button(onClick = { onMarcarFavorita(novela) }) {
            Text(if (novela.esFavorita) "Quitar de Favoritas" else "Marcar como Favorita")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Botón para volver a la pantalla principal
        Button(onClick = { onVolver() }) {
            Text(text = "Volver a la pantalla principal")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetallesNovela() {
    DetallesNovela(
        novela = Novela(titulo = "Novela ejemplo", autor = "autor ejemplo", anioPublicacion = 2023, sinopsis = "Esta es una sinopsis de ejemplo"),
        onMarcarFavorita = {},
        onVolver = {}
    )
}
