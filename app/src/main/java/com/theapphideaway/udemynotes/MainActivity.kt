package com.theapphideaway.udemynotes

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    var NoteList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //NoteList.add(Note(1,"First Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "))


       loadQuery("%")


    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    fun loadQuery(title:String){
        var dbManager=DbManager(this)
        val projections= arrayOf("Id","Title","Content")
        val selectionArgs= arrayOf(title)
        val cursor=dbManager.query(projections,"Title like ?",selectionArgs,"Title")
        NoteList.clear()
        if(cursor.moveToFirst()){

            do{
                val ID=cursor.getInt(cursor.getColumnIndex("Id"))
                val Title=cursor.getString(cursor.getColumnIndex("Title"))
                val Description=cursor.getString(cursor.getColumnIndex("Content"))

                NoteList.add(Note(ID,Title,Description))

            }while (cursor.moveToNext())
        }

        var noteList = findViewById<ListView>(R.id.notes_list)
        noteList.adapter = NoteAdapter(this,NoteList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.notes_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item != null){
            when(item.itemId){
                R.id.bar_button_add ->{
                    var intent = Intent(this, AddNote::class.java)

                    startActivity(intent)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class NoteAdapter: BaseAdapter {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var noteCard = layoutInflater.inflate(R.layout.ticket,null)
            var myNote = innerNoteArray[position]
            noteCard.title.text = myNote.Title
            noteCard.content.text = myNote.Content
            noteCard.delete_button.setOnClickListener(View.OnClickListener {
                var dbManager = DbManager(this.context!!)
                val selectionArgs= arrayOf(myNote.Id.toString())
                dbManager.delete("Id=?", selectionArgs )
                loadQuery("%")
            })
            noteCard.edit_button.setOnClickListener( View.OnClickListener {
                GoToUpdate(myNote)
            })

            return noteCard
        }

        override fun getItem(position: Int): Any {
            return innerNoteArray[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return innerNoteArray.count()
        }

        var innerNoteArray = ArrayList<Note>()
        var context: Context? = null

        constructor(context:Context, passedNoteArray:ArrayList<Note>):super(){
            this.innerNoteArray = passedNoteArray
            this.context = context
        }

    }

    fun GoToUpdate(note:Note){
        var intent = Intent(this, AddNote::class.java)
        intent.putExtra("Id", note.Id)
        intent.putExtra("Title", note.Title)
        intent.putExtra("Content", note.Content)
        startActivity(intent)
    }


}
