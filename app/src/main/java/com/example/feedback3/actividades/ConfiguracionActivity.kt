package com.example.feedback3.actividades

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.UsuarioDatabaseHelper
import com.example.feedback3.dataClasses.Usuario
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class ConfiguracionActivity : AppCompatActivity() {
    private lateinit var usuarioDbHelper: UsuarioDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        usuarioDbHelper = UsuarioDatabaseHelper(this)

        val btnBackup = findViewById<Button>(R.id.btnBackup)
        val btnRestore = findViewById<Button>(R.id.btnRestore)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        // Configurar botón de copias de seguridad
        btnBackup.setOnClickListener {
            realizarCopiaDeSeguridad()
        }

        //Configurar botón de restaurar datos
        btnRestore.setOnClickListener {
            restaurarDatos()
        }

        // Configurar el botón para volver a la pantalla anterior
        btnVolver.setOnClickListener {
            finish()  // Simplemente termina la actividad para volver a MainActivity
        }
    }

    private fun realizarCopiaDeSeguridad() {
        // Guardar un archivo de copia de seguridad con la información de los usuarios
        val usuarios = usuarioDbHelper.obtenerUsuarios()
        val backupFile = File(getExternalFilesDir(null), "copia_de_seguridad_usuarios.txt")

        try {
            FileOutputStream(backupFile).use { fos ->
                OutputStreamWriter(fos).use { writer ->
                    usuarios.forEach { usuario ->
                        writer.write("Email: ${usuario.email}, Password: ${usuario.password}, Tema Oscuro: ${usuario.temaOscuro}\n")
                    }
                }
            }
            Toast.makeText(this, "Copia de seguridad realizada en: ${backupFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al realizar la copia de seguridad: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun restaurarDatos() {
        val backupFile = File(getExternalFilesDir(null), "copia_de_seguridad_usuarios.txt")

        if (!backupFile.exists()) {
            Toast.makeText(this, "No se encontró ninguna copia de seguridad.", Toast.LENGTH_LONG).show()
            return
        }

        try {
            backupFile.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val datos = line.split(", ")
                    if (datos.size == 3) {
                        val email = datos[0].substringAfter("Email: ").trim()
                        val password = datos[1].substringAfter("Password: ").trim()
                        val temaOscuro = datos[2].substringAfter("Tema Oscuro: ").trim().toBoolean()

                        val usuario = Usuario(email, password, temaOscuro)
                        usuarioDbHelper.agregarUsuarioSiNoExiste(usuario)
                    }
                }
            }
            Toast.makeText(this, "Datos restaurados exitosamente desde la copia de seguridad.", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al restaurar los datos: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
