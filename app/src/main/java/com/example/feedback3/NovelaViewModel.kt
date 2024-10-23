package com.example.feedback3

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NovelaViewModel : ViewModel() {

    //Lista de novelas en memoria
    private val _novelas = mutableStateListOf<Novela>()
    val novelas: List<Novela> get() = _novelas

    //Función para cargar novelas desde Firebase
    fun cargarNovelasDesdeFirebase(onDataLoaded: (List<Novela>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Novelas")
            .get()
            .addOnSuccessListener { result ->
                val novelaData = result.map { document ->
                    Novela(
                        anioPublicacion = document.getLong("anioPublicacion")?.toInt() ?: 0,
                        autor = document.getString("autor") ?: "",
                        esFavorita = document.getBoolean("esFavorita") ?: false,
                        resenas = document.get("resenas") as? MutableList<String> ?: mutableListOf(),
                        sinopsis = document.getString("sinopsis") ?: "",
                        titulo = document.getString("titulo") ?: ""
                    )
                }
                onDataLoaded(novelaData)
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error obteniendo datos", exception)
            }
    }

    fun agregarNovela(novela: Novela) {
        _novelas.add(novela)
    }

    fun eliminarNovela(novela: Novela) {
        _novelas.remove(novela)
    }

    fun marcarFavorita(novela: Novela) {
        val index = _novelas.indexOf(novela)
        if (index >= 0) {
            _novelas[index] = _novelas[index].copy(esFavorita = !novela.esFavorita)
        }
    }

    fun agregarResena(novela: Novela, resena: String) {
        novela.resenas.add(resena)
    }
}
