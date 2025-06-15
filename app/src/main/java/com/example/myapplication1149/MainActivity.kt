package com.example.myapplication1149

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // 主餐選擇結果
        mainCourseLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val mainDishes = result.data?.getStringArrayListExtra("mainCourse") ?: arrayListOf()
                val mainText = if (mainDishes.isNotEmpty()) {
                    "主餐：" + mainDishes.joinToString("、")
                } else {
                    "主餐：無"
                }
                Log.d("MainCourseActivity", "主餐：$mainText")

                mainCourseText.text = mainText
            }
        }

        sideDishLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val sideDishes = result.data?.getStringArrayListExtra("sideDishes") ?: arrayListOf()
                val sideText = if (sideDishes.isNotEmpty()) {
                    "副餐：" + sideDishes.joinToString("、")
                } else {
                    "副餐：無"
                }
                sideDishText.text = sideText
            }
        }


        // 副餐多選結果
        sideDishLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val sideDishes = result.data?.getStringArrayListExtra("sideDishes") ?: arrayListOf()
                val sideText = if (sideDishes.isNotEmpty()) {
                    "副餐：" + sideDishes.joinToString("、")
                } else {
                    "副餐：無"
                }
                sideDishText.text = sideText
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
