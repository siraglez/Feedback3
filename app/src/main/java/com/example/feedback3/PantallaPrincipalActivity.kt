package com.example.feedback3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PantallaPrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NovelaAdapter
    private var novelas: MutableList<Novela> = mutableListOf()
    private lateinit var dbHelper: NovelaDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private var isNightMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        isNightMode = sharedPreferences.getBoolean("nightMode", false)
        setTheme(if (isNightMode) R.style.Theme_Night else R.style.Theme_Day)

        setContentView(R.layout.activity_pantalla_principal)

        // Inicializar la base de datos
        dbHelper = NovelaDatabaseHelper(this)

        // Inicializar el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNovelas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NovelaAdapter(novelas) { novela -> verDetalles(novela) }
        recyclerView.adapter = adapter

        // Cargar novelas desde la base de datos SQLite
        cargarNovelasDesdeSQLite()

        // Configurar el botón para agregar una nueva novela
        findViewById<Button>(R.id.btnAgregarNovela).setOnClickListener {
            val intent = Intent(this, AgregarNovelaActivity::class.java)
            startActivity(intent)
        }

        val btnCambiarTema = findViewById<Button>(R.id.btnCambiarTema)
        btnCambiarTema.text = if (isNightMode) "Noche" else "Día"

        btnCambiarTema.setOnClickListener {
            toggleTheme(btnCambiarTema)
        }
    }

    private fun toggleTheme(button: Button) {
        isNightMode = !isNightMode // Cambia el estado del tema
        sharedPreferences.edit().putBoolean("nightMode", isNightMode).apply() // Guarda la preferencia
        button.text = if (isNightMode) "Día" else "Noche"
        recreate() // Recrea la actividad para aplicar el nuevo tema
    }

    // Cargar todas las novelas desde la base de datos SQLite
    private fun cargarNovelasDesdeSQLite() {
        novelas.clear() // Limpiar la lista actual de novelas
        novelas.addAll(dbHelper.getAllNovelas()) // Agregar las novelas desde la base de datos a la lista
        adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
    }

    // Ver detalles de una novela seleccionada
    private fun verDetalles(novela: Novela) {
        val intent = Intent(this, DetallesNovelaActivity::class.java)
        intent.putExtra("titulo", novela.titulo)
        intent.putExtra("autor", novela.autor)
        intent.putExtra("anioPublicacion", novela.anioPublicacion)
        intent.putExtra("sinopsis", novela.sinopsis)
        intent.putExtra("esFavorita", novela.esFavorita)
        startActivity(intent)
    }

    // Refrescar la lista de novelas cuando se vuelve a esta actividad
    override fun onResume() {
        super.onResume()
        cargarNovelasDesdeSQLite()
    }
}
