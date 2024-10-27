package com.example.feedback3.actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R

class DetallesNovelaActivity : AppCompatActivity() {
    private lateinit var textViewTitulo: TextView
    private lateinit var textViewAutor: TextView
    private lateinit var textViewAnio: TextView
    private lateinit var textViewSinopsis: TextView
    private lateinit var btnMarcarFavorito: Button
    private lateinit var btnVolver: Button // Cambiado de ImageButton a Button

    private lateinit var titulo: String
    private lateinit var autor: String
    private var anio: Int = 0
    private lateinit var sinopsis: String
    private var esFavorita: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_novela)

        // Inicializar los TextViews y el botón
        textViewTitulo = findViewById(R.id.tvTitulo)
        textViewAutor = findViewById(R.id.tvAutor)
        textViewAnio = findViewById(R.id.tvAnio)
        textViewSinopsis = findViewById(R.id.tvSinopsis)
        btnMarcarFavorito = findViewById(R.id.btnMarcarFavorito)
        btnVolver = findViewById(R.id.btnVolver) // Aquí sigue siendo Button

        // Obtener los datos pasados desde el Intent
        titulo = intent.getStringExtra("titulo") ?: ""
        autor = intent.getStringExtra("autor") ?: ""
        anio = intent.getIntExtra("anio", 0)
        sinopsis = intent.getStringExtra("sinopsis") ?: ""
        esFavorita = intent.getBooleanExtra("esFavorita", false)

        // Log para depurar el año recibido
        Log.d("DetallesNovela", "Año recibido: $anio")

        // Mostrar los detalles de la novela
        mostrarDetallesNovela()

        // Configurar el botón para marcar como favorito
        btnMarcarFavorito.setOnClickListener {
            // Lógica para marcar o desmarcar como favorito
            esFavorita = !esFavorita // Cambia el estado
            actualizarEstadoFavorito()
        }

        // Configurar el botón para volver a la pantalla anterior
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDetallesNovela() {
        textViewTitulo.text = "Título: $titulo"
        textViewAutor.text = "Autor: $autor"
        textViewAnio.text = "Año: $anio"
        textViewSinopsis.text = "Sinopsis: $sinopsis"
        actualizarEstadoFavorito()
    }

    private fun actualizarEstadoFavorito() {
        btnMarcarFavorito.text = if (esFavorita) {
            "Desmarcar Favorito"
        } else {
            "Marcar como Favorito"
        }
    }
}
