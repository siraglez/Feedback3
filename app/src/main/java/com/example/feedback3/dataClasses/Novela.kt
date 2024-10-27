package com.example.feedback3.dataClasses

data class Novela(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int,
    val sinopsis: String,
    val esFavorita: Boolean = false,
    val resenas: MutableList<String> = mutableListOf()
)
