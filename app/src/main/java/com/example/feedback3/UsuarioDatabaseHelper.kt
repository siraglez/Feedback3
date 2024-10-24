package com.example.feedback3

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsuarioDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_USUARIOS = "Usuarios"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_USUARIOS ($COLUMN_EMAIL TEXT PRIMARY KEY, $COLUMN_PASSWORD TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    fun addUser(usuario: Usuario): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_EMAIL, usuario.email)
        contentValues.put(COLUMN_PASSWORD, usuario.password)

        val result = db.insert(TABLE_USUARIOS, null, contentValues)
        db.close()
        return result != -1L // Devuelve true si la inserción fue exitosa
    }

    fun validateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(TABLE_USUARIOS, null, "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", arrayOf(email, password), null, null, null)

        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists // Devuelve true si se encontró el usuario
    }

    fun getUser(email: String): Usuario? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USUARIOS,
            null,
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            usuario = Usuario(email, password)
        }

        cursor.close()
        db.close()
        return usuario
    }

    fun getAllUsers(): List<Usuario> {
        val userList = mutableListOf<Usuario>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_USUARIOS, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                val usuario = Usuario(email, password)
                userList.add(usuario)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return userList
    }
}