package com.theapphideaway.udemynotes

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {

    val dbTable = "MyNotes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)


        try{
            var bundle:Bundle=intent.extras
            id=bundle.getInt("Id",0)
            if(id!=0) {
                title_text.setText(bundle.getString("Title") )
                content_text.setText(bundle.getString("Content") )

            }
        }catch (ex:Exception){}

    }

    fun onSave(view: View){
        var dbManager = DbManager(this)

        var values = ContentValues()
        values.put("Title", title_text.text.toString())
        values.put("Content", content_text.text.toString())

        if (id == 0) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Couldn't added", Toast.LENGTH_SHORT).show()
            }
        } else {
            var selectionArgs = arrayOf(id.toString())
            val Id = dbManager.update(values, "Id=?", selectionArgs)
            if (Id > 0) {
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Couldn't added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
