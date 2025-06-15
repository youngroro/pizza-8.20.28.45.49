package com.example.myapplication1149

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainCourseActivity : AppCompatActivity() {

    private var selectedIndex: Int = -1  // 用 index 追蹤選擇哪一個

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_course)

        val items3 = arrayListOf(
            Item(R.drawable.pig, "泰式檸檬椒麻豬"),
            Item(R.drawable.cheese_beef, "經典費城起司牛肉"),
            Item(R.drawable.takoyaki, "和風章魚燒"),
            Item(R.drawable.seafood, "海鮮"),
            Item(R.drawable.gold_chicken, "金沙黃金脆雞"),
            Item(R.drawable.hawaii, "夏威夷")
        )

        class MyAdapter(context: Context, val layoutId: Int, val data: List<Item>) :
            ArrayAdapter<Item>(context, layoutId, data) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: View.inflate(context, layoutId, null)
                val item = getItem(position) ?: return view

                view.findViewById<ImageView>(R.id.item_image).setImageResource(item.photo)
                view.findViewById<TextView>(R.id.item_text).text = item.name
                val checkBox = view.findViewById<CheckBox>(R.id.item_checkbox)

                checkBox.setOnCheckedChangeListener(null)

                // 根據資料設定勾選狀態
                checkBox.isChecked = item.isChecked

                // 設定新的勾選變化監聽
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    item.isChecked = isChecked
                    val action = if (isChecked) "已勾選" else "已取消"
                    Toast.makeText(context, "${action}：${item.name}", Toast.LENGTH_SHORT).show()
                }

                return view
            }
        }

        val myAdapter = MyAdapter(this, R.layout.adapter_item, items3)
        val listView = findViewById<ListView>(R.id.listViewMainCourse)
        listView.adapter = myAdapter

//        listView.setOnItemClickListener { _, _, position, _ ->
//            selectedIndex = position
//            myAdapter.notifyDataSetChanged()
//            Toast.makeText(this, "已選擇：${items3[position].name}", Toast.LENGTH_SHORT).show()
//        }


        val btnConfirm = findViewById<Button>(R.id.btnConfirmMain)
        btnConfirm.setOnClickListener {
            // 篩選出有勾選的項目
            val selectedItems = items3.filter { it.isChecked }.map { it.name }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "請至少選擇一項主餐", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("MainCourseActivity", "選到的主餐：$selectedItems")
            // 有選擇才回傳資料
            val resultIntent = Intent()
            resultIntent.putStringArrayListExtra("mainCourse", ArrayList(selectedItems))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}
