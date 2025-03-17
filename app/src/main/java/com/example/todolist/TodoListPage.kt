package com.example.todolist


import androidx.compose.material3.Divider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource


import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun TodoListPage(viewModel: TodoViewModel){

    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(40.dp)

    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFF1EADC))
                    .padding(0.dp),
                value = inputText,
                onValueChange = {
                    inputText = it
                })

            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    viewModel.addTodo(inputText)
                    inputText = ""
                }) {
                Text(text = "Add")
            }
        }

        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it){ index: Int, item: Todo ->
                        TodoItem(item = item, onToggleComplete = { isChecked -> viewModel.toggleCompletion(item.id, isChecked) }, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 28.sp
            )

    }

}


@Composable
fun TodoItem(item : Todo, onToggleComplete: (Boolean) -> Unit, onDelete : ()-> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFF7F50))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/MM", Locale.ENGLISH).format(item.createdAt),
                fontSize = 14.sp,
                color = Color.Black
            )

            Box(modifier = Modifier.wrapContentSize()
            ) {

                    Text(
                        text = item.title,
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        textDecoration = TextDecoration.None,

                        )
                    if (item.isCompleted) {
                        Box(
                            modifier = Modifier
                                .matchParentSize() // Ensures the line matches the text width
                                .padding(top = 16.dp) // Adjust to align with the text
                        ) {
                            androidx.compose.material3.Divider(
                                color = Color.Black,  // Custom strike-through color
                                thickness = 3.dp,

                                )
                        }
                    }
            }
        }

        Checkbox(
            checked = item.isCompleted,
            onCheckedChange = { isChecked -> onToggleComplete(isChecked) }
        )

        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                contentDescription = "Delete",
                tint = Color.Black
            )
        }
    }
}

