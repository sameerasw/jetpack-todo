package com.sameerasw.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.sameerasw.todo.ui.theme.ToDoTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val ViewModel: TodoViewModel = TodoViewModel() // creates a view model instance
                AppUI(viewModel = ViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUI( modifier: Modifier = Modifier, viewModel: TodoViewModel) {
    var showAlert: Boolean by remember { mutableStateOf(false) }
//    var todoItems: List<TodoItem> by remember { mutableStateOf(listOf()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAlert = true
                },
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "ToDo") },
                modifier = modifier,
                navigationIcon = {
                    // Navigation Icon
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                },
                actions = {
                    // Action Icons
                }

            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }

        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text("Add a new ToDo") },
                text = {
                    Column {

                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                // update the value
                                title = it
                            },
                            label = { Text("New ToDo") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = {
                                // update the value
                                description = it
                            },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showAlert = false
                            // Add the new ToDo
                            viewModel.addTodo(
                                id = UUID.randomUUID().hashCode().toString(),
                                title = title,
                                description = description
                            )
                        }
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showAlert = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }


        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(viewModel.todos.value.size) { index ->
                val todoItem = viewModel.todos.value[index]
                ToDoItem(todoItem = todoItem)
            }
        }
    }
}

@Composable
fun ToDoItem(todoItem: TodoItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = todoItem.title,
                    fontSize = 24.sp,
                    fontWeight = Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = todoItem.description,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Checkbox(
                checked = todoItem.isCompleted,
                onCheckedChange = {
                    todoItem.toggle()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppUIPreview() {
    ToDoTheme {
        AppUI(viewModel = TodoViewModel())
    }
}