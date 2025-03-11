package com.example.todolist

import java.time.Instant

import java.util.Date

data class Todo(
    var id : Int,
    var title : String,
    var createdAt : Date
)

fun getFakeTodo() : List<Todo>{
    return listOf<Todo>(
        Todo( 1,  "first ",Date.from(Instant.now())),
        Todo( 2,  "second ",Date.from(Instant.now())),
        Todo( 3,  "third ",Date.from(Instant.now())),
        Todo( 4,  "fourth ",Date.from(Instant.now()))
    );
}
