package com.example.myapplication1149


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SideDishActivity : AppCompatActivity() {

    private val sideDishes = listOf("玉米濃湯", "薯條", "炸豆腐", "溏心蛋", "小菜拼盤")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_dish)

        val listView = findViewById<ListView>(R.id.listViewSideDish)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sideDishes)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selected = sideDishes[position]
            val intent = Intent().apply {
                putExtra("sideDish", selected)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}