package com.mashudi.animals

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreff
    var animals: Animals?=null
    companion object {
        const val REQUEST_IMAGE = 400
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
        setContentView(R.layout.activity_add_edit)
        animals=intent.getParcelableExtra(MainActivity.KEY_ANIMALS)
        supportActionBar?.apply {
            title = if(animals == null) "Add Animal" else "Edit Animal"
            animals?.apply {
                subtitle = nama
            }
//            if(animals!=null) {
//                subtitle=animals?.nama
//            }
            setDisplayHomeAsUpEnabled(true)
        }
        animals?.apply {
            addEditNama.setText(nama)
            addEditInggris.setText(inggris)
            addEditKeterangan.setText(desc)
            addEditUrl.setText(url)
            addEditGambar.setText(img)
        }

        buttonBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"), REQUEST_IMAGE)
        }
        buttonSave.setOnClickListener {
            if(animals == null) animals = Animals()
            animals?.apply {
                nama = addEditNama.text.toString()
                inggris = addEditInggris.text.toString()
                desc = addEditKeterangan.text.toString()
                url = addEditUrl.text.toString()
                img = addEditGambar.text.toString()
            }
            val intent = Intent()
            intent.putExtra(MainActivity.KEY_ANIMALS, animals)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUrl = data.data?.toString()
            addEditGambar.setText(imageUrl)
            Log.d("URL GAMBAR", data?.data.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}