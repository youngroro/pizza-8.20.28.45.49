package com.example.myapplication1149

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SideDishActivity : AppCompatActivity() {

    // 自訂的 Item 類別，加入 isChecked 支援多選
    data class Item(val photo: Int, val name: String, var isChecked: Boolean = false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_side_dish)

        val items3 = ArrayList<Item>()
        items3.add(Item(R.drawable.bbq, "BBQ烤雞"))
        items3.add(Item(R.drawable.qq, "麻糬QQ球"))
        items3.add(Item(R.drawable.soup, "玉米濃湯"))
        items3.add(Item(R.drawable.tart, "冰藏起司塔"))
        items3.add(Item(R.drawable.chickentender, "雞柳條"))

        class MyAdapter(context: Context, val layoutId: Int, val data: ArrayList<Item>)
            : ArrayAdapter<Item>(context, layoutId, data) {

            override fun getCount() = data.size
            override fun getItem(position: Int) = data[position]
            override fun getItemId(position: Int) = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: View.inflate(parent.context, layoutId, null)
                val item = getItem(position) ?: return view

                val imageView = view.findViewById<ImageView>(R.id.item_image)
                val textView = view.findViewById<TextView>(R.id.item_text)
                val checkBox = view.findViewById<CheckBox>(R.id.item_checkbox)

                imageView.setImageResource(item.photo)
                textView.text = item.name

                // 先移除監聽再綁定（避免重複觸發）
                checkBox.setOnCheckedChangeListener(null)
                checkBox.isChecked = item.isChecked
                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    item.isChecked = isChecked
                    val action = if (isChecked) "已勾選" else "已取消"
                    Toast.makeText(context, "${action}：${item.name}", Toast.LENGTH_SHORT).show()
                }
                return view
            }
        }

        val myAdapter = MyAdapter(this, R.layout.adapter_item, items3)
        val listView = findViewById<ListView>(R.id.listViewSideDish)
        listView.adapter = myAdapter

        // 點整行也能切換勾選狀態
        listView.setOnItemClickListener { _, _, position, _ ->
            items3[position].isChecked = !items3[position].isChecked
            myAdapter.notifyDataSetChanged()
        }

        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        btnConfirm.setOnClickListener {
            val selectedItems = items3.filter { it.isChecked }.map { it.name }

            val resultIntent = Intent()
            resultIntent.putStringArrayListExtra("sideDishes", ArrayList(selectedItems))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}
