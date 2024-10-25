package com.example.feedback3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetallesNovelaActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvSynopsis: TextView
    private lateinit var btnToggleFavorite: Button
    private lateinit var etReview: EditText
    private lateinit var btnAddReview: Button

    private lateinit var novela: Novela // Objeto novela
    private lateinit var dbHelper: NovelaDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.aplicarTema(this)
        setContentView(R.layout.activity_detalles_novela)

        // Inicializa las vistas
        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvYear = findViewById(R.id.tvYear)
        tvSynopsis = findViewById(R.id.tvSynopsis)
        btnToggleFavorite = findViewById(R.id.btnToggleFavorite)
        etReview = findViewById(R.id.etReview)
        btnAddReview = findViewById(R.id.btnAddReview)

        // Obtiene el objeto novela del Intent
        novela = intent.getParcelableExtra("novela") ?: return

        //Inicializar SQLite Helper
        dbHelper = NovelaDatabaseHelper(this)

        //Cargar detalles de la novela en las vistas
        loadNovelaDetails()

        // Configurar listeners
        btnToggleFavorite.setOnClickListener { toggleFavorite() }
        btnAddReview.setOnClickListener { addReview() }
    }

    private fun loadNovelaDetails() {
        tvTitle.text = novela.titulo
        tvAuthor.text = novela.autor
        tvYear.text = novela.anioPublicacion.toString()
        tvSynopsis.text = novela.sinopsis
        btnToggleFavorite.text = if (novela.esFavorita) "Desmarcar como Favorita" else "Marcar como Favorita"
    }

    private fun toggleFavorite() {
        novela.esFavorita = !novela.esFavorita
        novela.esFavorita = !novela.esFavorita
        val result = dbHelper.updateFavorite(novela)

        if (result > 0) {
            val message = if (novela.esFavorita) "Añadido a Favoritos" else "Eliminado de Favoritos"
            btnToggleFavorite.text = if (novela.esFavorita) "Desmarcar como Favorita" else "Marcar como Favorita"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al actualizar favorito", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addReview() {
        val reviewText = etReview.text.toString().trim()
        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Por favor escribe una reseña", Toast.LENGTH_SHORT).show()
            return
        }

        val result = dbHelper.addResena(novela.titulo, reviewText)

        if (result > 0) {
            etReview.text.clear() //Limpiar el campo de reseña
            Toast.makeText(this, "Reseña añadida", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al agregar la reseña", Toast.LENGTH_SHORT).show()
        }
    }
}