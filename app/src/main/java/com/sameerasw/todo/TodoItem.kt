package com.sameerasw.todo

import android.util.Log

data class TodoItem(
    val id: String,
    val title: String,
    val description: String,
    var isCompleted: Boolean
){
    // custom functions/ getters and setters here

    fun complete(){
        this.isCompleted = true
        Log.e("TodoItem : ", "Completed")
    }

    fun toggle(){
        this.isCompleted = !isCompleted
        Log.e("TodoItem : ", "Toggled to $isCompleted")
    }
}


