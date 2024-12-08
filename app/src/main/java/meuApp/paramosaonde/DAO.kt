package meuApp.paramosaonde

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DAO(context: Context) : SQLiteOpenHelper(context, "epp.db", null, 1)  {

    companion object{

        private const val TABLE_SHOW = "shows"


    }

    private val createTable = arrayOf(
        """CREATE TABLE $TABLE_SHOW (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, imgUri TEXT, ep INTEGER)""".trimIndent()
    )

    override fun onCreate(db: SQLiteDatabase?) {
        createTable.forEach {
            db?.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = arrayOf("DROP TABLE IF EXISTS $TABLE_SHOW")

        dropTable.forEach {
            db?.execSQL(it)
        }
        onCreate(db)
    }

    // Adiciona um show
    fun addShow(title: String, imgUri: String, ep: String): Long{
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("title", title)
        values.put("imgUri", imgUri)
        values.put("ep", ep)

        val result = db.insert("show", null, values)
        db.close()

        return result

    }

    // Mostra os shows cadastrados
    fun getShows(): List<Show>{
        val shows = mutableListOf<Show>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SHOW", null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(0)
                val title = cursor.getString(1)
                val imgUri = cursor.getString(2)
                val ep = cursor.getInt(3)
                shows.add(Show(id, title, imgUri, ep))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return shows
    }

    // Atualiza o episodio do show
    fun updateEp(ep: Int, id: Int): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("ep", ep)
        db.update("show", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return 1
    }

    // Atualiza o show
    fun updateShow(title: String, imgUri: String, ep: String, id: Int){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("imgUri", imgUri)
        values.put("ep", ep)
        db.update("show", values, "id = ?", arrayOf(id.toString()))

        db.close()

    }

    // Deleta o show
    fun deleteShow(id: Int){
        val db = this.writableDatabase
        db.delete("show", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    /*
    fun updateImg(id: Int, img: String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("img", img)
        db.update(TABLE_SHOW, values, "id = ?", arrayOf(id.toString()))
        db.close()
    }
     */

}