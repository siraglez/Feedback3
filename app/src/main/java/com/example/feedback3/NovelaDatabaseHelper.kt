package com.example.feedback3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NovelaDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "novelas.db"
        private const val DATABASE_VERSION = 2 // Incrementa la versión si haces cambios

        // Tabla
        private const val TABLE_NOVELAS = "novelas"

        // Columnas de la tabla "novelas"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_AUTOR = "autor"
        private const val COLUMN_ANIO_PUBLICACION = "anioPublicacion"
        private const val COLUMN_SINOPSIS = "sinopsis"
        private const val COLUMN_ES_FAVORITA = "esFavorita"
        private const val COLUMN_RESENAS = "resenas" // Columna para las reseñas
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createNovelasTable = """
            CREATE TABLE $TABLE_NOVELAS (
            $COLUMN_TITULO TEXT PRIMARY KEY,
            $COLUMN_AUTOR TEXT,
            $COLUMN_ANIO_PUBLICACION INTEGER,
            $COLUMN_SINOPSIS TEXT,
            $COLUMN_ES_FAVORITA INTEGER,
            $COLUMN_RESENAS TEXT 
        )
        """
        db?.execSQL(createNovelasTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOVELAS")
        onCreate(db)
    }

    // Función para insertar una novela
    fun addNovela(novela: Novela): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TITULO, novela.titulo)
        contentValues.put(COLUMN_AUTOR, novela.autor)
        contentValues.put(COLUMN_ANIO_PUBLICACION, novela.anioPublicacion)
        contentValues.put(COLUMN_SINOPSIS, novela.sinopsis)
        contentValues.put(COLUMN_ES_FAVORITA, if (novela.esFavorita) 1 else 0)
        contentValues.put(COLUMN_RESENAS, "") // Inicializa las reseñas como vacías

        val result = db.insert(TABLE_NOVELAS, null, contentValues)
        db.close()
        return result != -1L // Devuelve true si la inserción fue exitosa
    }

    fun getAllNovelas(): List<Novela> {
        val novelList = mutableListOf<Novela>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NOVELAS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                val autor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTOR))
                val anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ANIO_PUBLICACION))
                val sinopsis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SINOPSIS))
                val resenas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESENAS))
                val esFavoritaInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ES_FAVORITA))

                val esFavorita = esFavoritaInt == 1

                val novela = Novela(
                    titulo = titulo,
                    autor = autor,
                    anioPublicacion = anioPublicacion,
                    sinopsis = sinopsis,
                    resenas = resenas.split("\n"), // Convierte la cadena de reseñas en lista
                    esFavorita = esFavorita
                )

                novelList.add(novela)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return novelList
    }

    fun deleteNovela(titulo: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NOVELAS, "$COLUMN_TITULO = ?", arrayOf(titulo))
        db.close()
        return result > 0
    }

    // Función para actualizar el estado de favorito
    fun updateFavorite(novela: Novela): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ES_FAVORITA, if (novela.esFavorita) 1 else 0)

        return db.update(TABLE_NOVELAS, values, "$COLUMN_TITULO = ?", arrayOf(novela.titulo))
    }

    // Función para agregar una reseña
    fun addResena(novelaTitulo: String, resena: String): Long {
        val db = writableDatabase
        val currentResenas = getResenasPorTitulo(novelaTitulo).joinToString("\n") // Obtiene reseñas actuales
        val newResenas = if (currentResenas.isNotEmpty()) "$currentResenas\n$resena" else resena

        val values = ContentValues()
        values.put(COLUMN_RESENAS, newResenas)

        return db.update(TABLE_NOVELAS, values, "$COLUMN_TITULO = ?", arrayOf(novelaTitulo)).toLong()
    }

    // Función para cargar las reseñas de una novela
    fun getResenasPorTitulo(titulo: String): List<String> {
        val resenasList = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NOVELAS, arrayOf(COLUMN_RESENAS), "$COLUMN_TITULO = ?", arrayOf(titulo), null, null, null
        )

        if (cursor.moveToFirst()) {
            val resenas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESENAS))
            resenasList.addAll(resenas.split("\n")) // Divide la cadena en una lista
        }
        cursor.close()
        db.close()
        return resenasList
    }

    // Función para actualizar los datos de una novela
    fun actualizarNovela(novela: Novela): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, novela.titulo)
            put(COLUMN_AUTOR, novela.autor)
            put(COLUMN_ANIO_PUBLICACION, novela.anioPublicacion)
            put(COLUMN_SINOPSIS, novela.sinopsis)
            put(COLUMN_ES_FAVORITA, if (novela.esFavorita) 1 else 0)
            put(COLUMN_RESENAS, novela.resenas.joinToString("\n")) // Guarda las reseñas como cadena
        }

        val result = db.update(TABLE_NOVELAS, values, "$COLUMN_TITULO = ?", arrayOf(novela.titulo))
        return result > 0
    }
}
