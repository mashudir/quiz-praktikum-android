package com.mashudi.animals

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref : SharedPreff
    lateinit var animalsDAO: AnimalsDAO
    lateinit var adapter: AnimlasAdapter
    // companion object = public static in java
    companion object {
        const val REQUEST_ADD = 100
        const val REQUEST_EDIT = 200
        const val REQUEST_REMOVE = 300
        const val REQUEST_DETAIL = 500
        const val KEY_ANIMALS = "animals"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreff(this)

        if (sharedPref.dark){
            setTheme(R.style.Darkmode)
        }
        else{
            setTheme(R.style.Theme_Animals)
        }
        setContentView(R.layout.activity_main)
        animalsDAO = AppDatabase.getInstance(this).animalsDao()
        recyclerView.layoutManager = LinearLayoutManager(this)
        //val list = AnimalsData.list
        //val adapter = AnimlasAdapter(list,sharedPref)
        adapter = AnimlasAdapter(animalsDAO.selectAll(),sharedPref)
        recyclerView.adapter = adapter
        //recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        adapter.onItemClickListener = {
//            Toast.makeText(this,it.nama,Toast.LENGTH_SHORT).show()
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra(KEY_ANIMALS,it)
            startActivityForResult(intent, REQUEST_ADD)
        }
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD)
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.layoutManager = if (sharedPref.gridLayout && sharedPref.column > 0) {
            GridLayoutManager(this, sharedPref.column)
        }else {
            LinearLayoutManager(this)
        }
        //untuk pengaturan app name
        supportActionBar?.title = sharedPref.appName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK && data != null){
            //Tambah data
            val animals = data.getParcelableExtra<Animals>(KEY_ANIMALS)
            animals?.apply {
                animalsDAO.insert(this)
            }
            Toast.makeText(this,"Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
        adapter.list = animalsDAO.selectAll()
        adapter.notifyDataSetChanged()
    }
// memasang icon setting ke main activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    // memberikan action jika icon setting pojok kanan atas di click, kasus ini jika di klik akan membuak activity baru
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuSettings -> startActivity(Intent(this, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}