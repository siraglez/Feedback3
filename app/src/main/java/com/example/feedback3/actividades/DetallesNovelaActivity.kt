package com.example.feedback3.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.dataClasses.Novela

class DetallesNovelaActivity : AppCompatActivity() {
    private lateinit var textViewTitulo: TextView
    private lateinit var textViewAutor: TextView
    private lateinit var textViewAnio: TextView
    private lateinit var textViewSinopsis: TextView
    private lateinit var btnMarcarFavorito: Button
    private lateinit var btnVolver: ImageButton
    private lateinit var novela: Novela

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_novela)

        // Obtener la novela pasada desde el Intent
        novela = intent.getSerializableExtra("novela") as Novela

        // Inicializar los TextViews y el botón
        textViewTitulo = findViewById(R.id.tvTitulo)
        textViewAutor = findViewById(R.id.tvAutor)
        textViewAnio = findViewById(R.id.tvAnio)
        textViewSinopsis = findViewById(R.id.tvSinopsis)
        btnMarcarFavorito = findViewById(R.id.btnMarcarFavorito)
        btnVolver = findViewById(R.id.btnVolver)

        // Mostrar los detalles de la novela
        mostrarDetallesNovela()

        // Configurar el botón para marcar como favorito
        btnMarcarFavorito.setOnClickListener {
            // Lógica para marcar o desmarcar como favorito
            novela.esFavorita = !novela.esFavorita // Cambia el estado
            actualizarEstadoFavorito()
        }

        // Configurar el botón para volver a la pantalla anterior
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDetallesNovela() {
        textViewTitulo.text = novela.titulo
        textViewAutor.text = novela.autor
        textViewAnio.text = novela.anioPublicacion.toString()
        textViewSinopsis.text = novela.sinopsis
        actualizarEstadoFavorito()
    }

    private fun actualizarEstadoFavorito() {
        btnMarcarFavorito.text = if (novela.esFavorita) {
            "Desmarcar Favorito"
        } else {
            "Marcar como Favorito"
        }
    }
}
