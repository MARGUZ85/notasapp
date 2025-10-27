//Nombre de Archivo NotasDatabaseHelper.kt
package com.example.notasapp.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notasapp.Data.NotaSQLite


class NotasDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    IDatabaseHelper {

    companion object {
        private const val DATABASE_NAME = "notas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTAS = "notas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NOTAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_DESCRIPCION TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTAS")
        onCreate(db)
    }

    override fun insertNota(titulo: String, descripcion: String) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(COLUMN_TITULO, titulo)
                put(COLUMN_DESCRIPCION, descripcion)
            }
            db.insert(TABLE_NOTAS, null, values)
        }
    }

    override fun getAllNotas(): List<NotaSQLite> {
        val lista = mutableListOf<NotaSQLite>()
        readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM $TABLE_NOTAS", null).use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                    val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                    lista.add(NotaSQLite(id, titulo, descripcion))
                }
            }
        }
        return lista
    }

    override fun getNotaById(id: Int): NotaSQLite? {
        readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM $TABLE_NOTAS WHERE $COLUMN_ID = ?", arrayOf(id.toString())).use { cursor ->
                return if (cursor.moveToFirst()) {
                    val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
                    val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
                    NotaSQLite(id, titulo, descripcion)
                } else null
            }
        }
    }

    override fun updateNota(id: Int, titulo: String, descripcion: String) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(COLUMN_TITULO, titulo)
                put(COLUMN_DESCRIPCION, descripcion)
            }
            db.update(TABLE_NOTAS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        }
    }

    override fun deleteNota(id: Int) {
        writableDatabase.use { db ->
            db.delete(TABLE_NOTAS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        }
    }
}
