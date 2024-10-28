package com.example.feedback3.baseDeDatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.feedback3.dataClasses.Novela

class NovelaDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "novelas.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NOVELAS = "novelas"
        private const val COLUMN_TITULO = "titulo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NOVELAS (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITULO TEXT, " +
                    "autor TEXT, " +
                    "anioPublicacion INTEGER, " +
                    "sinopsis TEXT, " +
                    "esFavorita INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NOVELAS")
            onCreate(db)
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NOVELAS")
            onCreate(db)
        }
    }

    // Agregar una novela
    fun agregarNovela(novela: Novela) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, novela.titulo)
            put("autor", novela.autor)
            put("anioPublicacion", novela.anioPublicacion)
            put("sinopsis", novela.sinopsis)
            put("esFavorita", if (novela.esFavorita) 1 else 0)
        }
        db.insert(TABLE_NOVELAS, null, values)
        db.close()
    }

    // Eliminar una novela por su título
    fun eliminarNovela(titulo: String): Boolean {
        val db = writableDatabase
        val rowsDeleted = db.delete(TABLE_NOVELAS, "$COLUMN_TITULO = ?", arrayOf(titulo))
        db.close()
        return rowsDeleted > 0
    }

    // Obtener la lista de novelas
    fun obtenerNovelas(): List<Novela> {
        val novelas = mutableListOf<Novela>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NOVELAS", null)

        if (cursor.moveToFirst()) {
            do {
                val novela = Novela(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    cursor.getString(cursor.getColumnIndexOrThrow("autor")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("anioPublicacion")),
                    cursor.getString(cursor.getColumnIndexOrThrow("sinopsis")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("esFavorita")) == 1
                )
                novelas.add(novela)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return novelas
    }

    //Actualizar el estado de favorito de las novelas
    fun actualizarFavorito(titulo: String, esFavorita: Boolean): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("esFavorita", if (esFavorita) 1 else 0)
        }
        val rowsUpdated = db.update("novelas", values, "titulo = ?", arrayOf(titulo))
        db.close()
        return rowsUpdated > 0
    }
}
