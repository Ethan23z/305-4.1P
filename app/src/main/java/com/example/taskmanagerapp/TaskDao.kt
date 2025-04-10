package com.example.taskmanagerapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    suspend fun getAllTasks(): List<TaskEntity>

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
