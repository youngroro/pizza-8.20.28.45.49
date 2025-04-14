package com.example.myapplication1149

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainCourseActivity : AppCompatActivity() {

    private val mainCourses = listOf("牛肉麵", "雞腿飯", "義大利麵", "咖哩飯", "排骨便當")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_course)

        val listView = findViewById<ListView>(R.id.listViewMainCourse)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mainCourses)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selected = mainCourses[position]
            val intent = Intent().apply {
                putExtra("mainCourse", selected)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
