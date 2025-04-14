package com.example.myapplication1149

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private lateinit var mainCourseText: TextView
    private lateinit var sideDishText: TextView

    private lateinit var mainCourseLauncher: ActivityResultLauncher<Intent>
    private lateinit var sideDishLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainCourseText = findViewById(R.id.tvMainCourse)
        sideDishText = findViewById(R.id.tvSideDish)

        // 明確註冊並指定泛型
        mainCourseLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val main = result.data?.getStringExtra("mainCourse") ?: ""
                mainCourseText.text = "主餐：$main"
            }
        }

        sideDishLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val side = result.data?.getStringExtra("sideDish") ?: ""
                sideDishText.text = "副餐：$side"
            }
        }

        findViewById<Button>(R.id.btnMainCourse).setOnClickListener {
            val intent = Intent(this, MainCourseActivity::class.java)
            mainCourseLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnSideDish).setOnClickListener {
            val intent = Intent(this, SideDishActivity::class.java)
            sideDishLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnStoreInfo).setOnClickListener {
            startActivity(Intent(this, StoreListActivity::class.java))
        }
    }
}
