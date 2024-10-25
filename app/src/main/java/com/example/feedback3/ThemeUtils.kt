package com.example.feedback3

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {

    //Función para guardar la preferencia del tema
    fun guardarPreferenciaTema(context: Context, esModoNoche: Boolean) {
        val prefs = context.getSharedPreferences("PreferenciasTema", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean("modo_noche", esModoNoche)
            apply()
        }
    }

    //Función para obtener la preferencia del tema
    fun obtenerPreferenciaTema(context: Context): Boolean {
        val prefs = context.getSharedPreferences("PreferenciasTema", Context.MODE_PRIVATE)
        return prefs.getBoolean("modo_noche", false)
    }

    //Función para aplicar el tema en base a la preferencia guardada
    fun aplicarTema(context: Context) {
        val esModoNoche = obtenerPreferenciaTema(context)
        AppCompatDelegate.setDefaultNightMode(
            if (esModoNoche) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}