package com.example.feedback3.actividades

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.UsuarioDatabaseHelper
import com.example.feedback3.baseDeDatos.NovelaDatabaseHelper

class DetallesNovelaActivity : AppCompatActivity() {
    private lateinit var textViewTitulo: TextView
    private lateinit var textViewAutor: TextView
    private lateinit var textViewAnio: TextView
    private lateinit var textViewSinopsis: TextView
    private lateinit var btnMarcarFavorito: Button
    private lateinit var btnVolver: Button
    private lateinit var btnEliminarNovela: Button
    private lateinit var btnAgregarResena: Button

    private lateinit var usuarioDbHelper: UsuarioDatabaseHelper
    private lateinit var novelaDbHelper: NovelaDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var titulo: String
    private lateinit var autor: String
    private var anio: Int = 0
    private lateinit var sinopsis: String
    private var esFavorita: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)
        aplicarTema()
        recreate()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_novela)

        usuarioDbHelper = UsuarioDatabaseHelper(this)
        novelaDbHelper = NovelaDatabaseHelper(this)

        // Inicializar los TextViews y los botones
        textViewTitulo = findViewById(R.id.tvTitulo)
        textViewAutor = findViewById(R.id.tvAutor)
        textViewAnio = findViewById(R.id.tvAnio)
        textViewSinopsis = findViewById(R.id.tvSinopsis)
        btnMarcarFavorito = findViewById(R.id.btnMarcarFavorito)
        btnVolver = findViewById(R.id.btnVolver)
        btnEliminarNovela = findViewById(R.id.btnEliminarNovela)
        btnAgregarResena = findViewById(R.id.btnAgregarResena)

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
            esFavorita = !esFavorita // Cambia el estado
            actualizarEstadoFavorito()

            // Envía el resultado de la actualización a MainActivity
            val resultIntent = Intent().apply {
                putExtra("favorito_actualizado", true)
            }
            setResult(RESULT_OK, resultIntent)
        }

        // Configurar el botón para volver a la pantalla anterior
        btnVolver.setOnClickListener {
            finish()  // Simplemente termina la actividad para volver a MainActivity
        }

        // Configurar el botón para eliminar la novela
        btnEliminarNovela.setOnClickListener {
            eliminarNovela()
        }

        //Configurar el botón para agregar reseñas
        btnAgregarResena.setOnClickListener {
            val intent = Intent(this, AgregarResenaActivity::class.java)
            intent.putExtra("tituloNovela", titulo)
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
        // Actualiza el botón para reflejar el estado de favorito
        btnMarcarFavorito.text = if (esFavorita) {
            "Desmarcar Favorito"
        } else {
            "Marcar como Favorito"
        }

        // Guarda el cambio en la base de datos
        val actualizado = novelaDbHelper.actualizarFavorito(titulo, esFavorita)
        if (!actualizado) {
            Toast.makeText(this, "Error al actualizar favorito", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarNovela() {
        val eliminado = novelaDbHelper.eliminarNovela(titulo)
        if (eliminado) {
            Toast.makeText(this, "Novela eliminada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Error al eliminar la novela", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aplicarTema() {
        val temaOscuro = sharedPreferences.getBoolean("temaOscuro", false)
        setTheme(if (temaOscuro) R.style.Theme_Feedback3_Night else R.style.Theme_Feedback3_Day)
    }
}
