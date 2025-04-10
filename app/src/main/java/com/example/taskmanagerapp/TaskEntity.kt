package com.example.taskmanagerapp

data class TaskEntity(
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: String
)
