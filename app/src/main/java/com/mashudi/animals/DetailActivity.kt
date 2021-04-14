package com.mashudi.animals

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class DetailActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreff
    lateinit var animalsDAO: AnimalsDAO
    var animals: Animals?=null
    var imgUrl=""
    val PERMISSION_REQUEST_CODE = 0
    var deskripsi=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPreff(this)

        if (sharedPref.dark){
            setTheme(R.style.Darkmode)
        }
        else{
            setTheme(R.style.Theme_Animals)
        }
        setContentView(R.layout.activity_detail)
        if (checkPermission()){
            requestPermissionAndContinue()
        }
        animalsDAO = AppDatabase.getInstance(this).animalsDao()
        animals = intent.getParcelableExtra<Animals>(MainActivity.KEY_ANIMALS)
        populate(animals)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

    }

    fun populate(animals: Animals?) {
        animals?.apply {
            Glide.with(this@DetailActivity).load(img).into(detailImg)
            detailName.text = nama
            detailInggris.text = inggris
            detailDesc.text = desc
            detailUrl.text = url
            imgUrl = img
            deskripsi = desc
        }

        supportActionBar?.apply {
            title = animals?.nama
            subtitle = animals?.inggris
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuEdit -> {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.putExtra(MainActivity.KEY_ANIMALS, animals)
                startActivityForResult(intent, MainActivity.REQUEST_EDIT)
            }

            R.id.menuShare -> {
                val poto = imgLayout
                poto.setDrawingCacheEnabled(true)
                val bm: Bitmap = poto.getDrawingCache()
                val photoDir = Environment
                    .getExternalStorageDirectory()
                val outputDir = File(photoDir, "ANIMALS")
                outputDir.mkdir()
                try {
                    val direktori =
                        File(outputDir, "ANIMALS_${SystemClock.currentThreadTimeMillis()}.jpg")
                    bm.compress(
                        Bitmap.CompressFormat.JPEG,
                        100,
                        FileOutputStream(direktori)
                    )
                    val whatsappIntent = Intent(Intent.ACTION_SEND)
                    whatsappIntent.type = "text/plain"
                    whatsappIntent.setPackage("com.whatsapp")
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "$deskripsi")
                    whatsappIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(direktori.absolutePath))
                    whatsappIntent.type = "image/*"
                    whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    try {
                        startActivity(whatsappIntent)
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            applicationContext,
                            "Whatsapp tidak terinstall",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                catch (e:Exception){
                    Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            R.id.menuRemove ->
                {
                AlertDialog.Builder(this)
                    .setMessage("Apakah anda yakin menghapus item ini?")
                    .setPositiveButton("Ya") {_,_ ->
                        //Hapus data
                        animals?.apply {
                            animalsDAO.delete(this)
                        }
                        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton("Tidak",null)
                    .show()
            }

            else -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MainActivity.REQUEST_EDIT && resultCode == Activity.RESULT_OK && data != null) {
            animals = data.getParcelableExtra(MainActivity.KEY_ANIMALS)
            populate(animals)
            //Update data
            animals?.apply {
                animalsDAO.update(this)
            }
            Toast.makeText(this,"Data berhasil diupdate", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

        private fun checkPermission(): Boolean {
            return (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
        }

        private fun requestPermissionAndContinue() {
            if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)


                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
                }
            } else {
//            openActivity()
            }
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (permissions.size > 0 && grantResults.size > 0) {
                    var flag = true
                    for (i in grantResults.indices) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            flag = false
                        }
                    }
                    if (flag) {
//                    openActivity()
                    } else {
                        finish()
                    }
                } else {
                    finish()
                }
            } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
}