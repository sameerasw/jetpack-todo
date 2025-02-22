package com.sameerasw.todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    private var _todos = mutableStateOf<List<TodoItem>>(emptyList())

    val todos : State<List<TodoItem>> = _todos

    fun addTodo(id: String, title: String, description: String){
        val newTodo = TodoItem(
            id = id,
            title = title,
            description = description,
            isCompleted = false
        )
        _todos.value = _todos.value + newTodo
    }

    fun toggleTodoComplete(todo: TodoItem){
        val toggledTodo = todo.copy(isCompleted = !todo.isCompleted)
        val updatedTodos = _todos.value.map {
            if(it.id == todo.id){
                toggledTodo
            }else{
                it
            }
        }
        _todos.value = updatedTodos
    }
}