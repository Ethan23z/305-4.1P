package com.example.taskmanagerapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagerapp.ui.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化 RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList()) // 空列表初始化
        recyclerView.adapter = taskAdapter

        // 获取 DAO
        taskDao = TaskDatabase.getDatabase(this).taskDao()

        // 悬浮按钮跳转添加任务
        findViewById<FloatingActionButton>(R.id.fabAddTask).setOnClickListener {
            startActivity(Intent(this, com.example.taskmanagerapp.ui.AddEditTaskActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        // 每次返回主界面时刷新任务
        lifecycleScope.launch {
            val tasks = taskDao.getAllTasks() // 获取数据库中所有任务
            runOnUiThread {
                taskAdapter.updateTasks(tasks)
            }
        }
    }
}
