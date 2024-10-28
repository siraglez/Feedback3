package com.example.feedback3.actividades

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.feedback3.R
import com.example.feedback3.baseDeDatos.UsuarioDatabaseHelper
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class ConfiguracionActivity : AppCompatActivity() {
    private lateinit var usuarioDbHelper: UsuarioDatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializar sharedPreferences antes de usarla
        sharedPreferences = getSharedPreferences("UsuarioPreferences", MODE_PRIVATE)

        // Acceder a sharedPreferences para obtener el valor de temaOscuro
        val temaOscuro = sharedPreferences.getBoolean("temaOscuro", false)
        aplicarTema(temaOscuro)  // Aplicar el tema antes de llamar a super.onCreate()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        usuarioDbHelper = UsuarioDatabaseHelper(this)

        val switchTemaNoche = findViewById<Switch>(R.id.switchTema)
        val btnBackup = findViewById<Button>(R.id.btnBackup)

        switchTemaNoche.isChecked = temaOscuro

        switchTemaNoche.setOnCheckedChangeListener { _, isChecked ->
            // Actualiza el valor del tema oscuro en SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("temaOscuro", isChecked)
            editor.apply()

            // Aplica el tema y recrea la actividad
            aplicarTema(isChecked)
            recreate()  // Recrear para aplicar el nuevo tema
        }

        // Configurar botón de copias de seguridad
        btnBackup.setOnClickListener {
            realizarCopiaDeSeguridad()
        }
    }

    private fun aplicarTema(temaOscuro: Boolean) {
        // Cambiar el tema de acuerdo a la preferencia del usuario
        if (temaOscuro) {
            setTheme(R.style.Theme_Feedback3_Night) // Aplicar tema oscuro
        } else {
            setTheme(R.style.Theme_Feedback3_Day) // Aplicar tema claro
        }
        // Aquí se puede utilizar AppCompatDelegate si se desea cambiar la configuración a nivel de aplicación
        AppCompatDelegate.setDefaultNightMode(
            if (temaOscuro) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun realizarCopiaDeSeguridad() {
        // Aquí guardamos un archivo de copia de seguridad con la información de los usuarios
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
}
