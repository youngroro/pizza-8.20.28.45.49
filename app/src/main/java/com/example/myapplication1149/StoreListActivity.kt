package com.example.myapplication1149

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

data class Store(val name: String, val phone: String)

class StoreListActivity : AppCompatActivity() {

    private val storeList = mutableListOf<Store>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_list)

        val listView = findViewById<ListView>(R.id.listViewStores)
        val addButton = findViewById<Button>(R.id.btnAddStore)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getStoreDisplayList())
        listView.adapter = adapter

        addButton.setOnClickListener {
            showStoreDialog(isUpdate = false)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val phone = storeList[position].phone
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(dialIntent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            showStoreDialog(isUpdate = true, position = position)
            true
        }
    }

    private fun showStoreDialog(isUpdate: Boolean, position: Int = -1) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_store, null)
        val etName = dialogView.findViewById<EditText>(R.id.etStoreName)
        val etPhone = dialogView.findViewById<EditText>(R.id.etStorePhone)

        if (isUpdate && position != -1) {
            val store = storeList[position]
            etName.setText(store.name)
            etPhone.setText(store.phone)
        }

        AlertDialog.Builder(this)
            .setTitle(if (isUpdate) "更新店家" else "新增店家")
            .setView(dialogView)
            .setPositiveButton(if (isUpdate) "更新" else "新增") { _, _ ->
                val name = etName.text.toString()
                val phone = etPhone.text.toString()
                if (isUpdate && position != -1) {
                    storeList[position] = Store(name, phone)
                } else {
                    storeList.add(Store(name, phone))
                }
                refreshList()
            }
            .setNegativeButton("取消", null)
            .show()
    }

    private fun refreshList() {
        adapter.clear()
        adapter.addAll(getStoreDisplayList())
        adapter.notifyDataSetChanged()
    }

    private fun getStoreDisplayList(): List<String> {
        return storeList.map { "${it.name}（${it.phone}）" }
    }
}