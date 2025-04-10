package com.example.taskmanagerapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.taskmanagerapp.R
import com.example.taskmanagerapp.TaskEntity
import com.example.taskmanagerapp.TaskDatabase
import kotlinx.coroutines.launch

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var edtTitle: EditText
    private lateinit var edtDescription: EditText
    private lateinit var edtDueDate: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        // 绑定控件
        edtTitle = findViewById(R.id.edtTitle)
        edtDescription = findViewById(R.id.edtDescription)
        edtDueDate = findViewById(R.id.edtDueDate)
        btnSave = findViewById(R.id.btnSave)

        val taskDao = TaskDatabase.getDatabase(this).taskDao()

        btnSave.setOnClickListener {
            val title = edtTitle.text.toString().trim()
            val description = edtDescription.text.toString().trim()
            val dueDate = edtDueDate.text.toString().trim()

            // 验证输入
            if (title.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "标题和截止日期不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = TaskEntity(
                title = title,
                description = description,
                dueDate = dueDate
            )

            // 使用协程保存任务
            lifecycleScope.launch {
                taskDao.insertTask(task)

                // 返回主页面 & 提示
                runOnUiThread {
                    Toast.makeText(this@AddEditTaskActivity, "任务已保存", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
