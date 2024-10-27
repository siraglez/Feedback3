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
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE novelas (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, autor TEXT, anioPublicacion INTEGER, sinopsis TEXT, esFavorita INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("DROP TABLE IF EXISTS novelas")
            onCreate(db)
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS novelas")
            onCreate(db)
        }
    }

    //Métodos para agregar, eliminar y obtener novelas
    fun agregarNovela(novela: Novela) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("titulo", novela.titulo)
            put("autor", novela.autor)
            put("anioPublicacion", novela.anioPublicacion)
            put("sinopsis", novela.sinopsis)
            put("esFavorita", if (novela.esFavorita) 1 else 0)
        }
        db.insert("novelas", null, values)
        db.close()
    }

    fun obtenerNovelas(): List<Novela> {
        val novelas = mutableListOf<Novela>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM novelas", null)

        if (cursor.moveToFirst()) {
            do {
                val novela = Novela(
                    cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
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

    fun eliminarNovela(titulo: String) {
        val db = writableDatabase
        db.delete("novelas", "titulo = ?", arrayOf(titulo))
        db.close()
    }
}