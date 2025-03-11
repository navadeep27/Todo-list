package com.example.todolist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TodoListPage(){
    val todoList = getFakeTodo()
    Text(text = todoList.toString())
}