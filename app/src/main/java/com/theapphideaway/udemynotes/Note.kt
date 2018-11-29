package com.theapphideaway.udemynotes

class Note {

    var Id: Int? = null
    var Title: String? = null
    var Content:String? = null

    constructor(id:Int?, title:String, content:String){
        Id = id
        Title = title
        Content = content
    }

}