package com.mashudi.animals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = SharedPreff(applicationContext)
        if (sharedPref.dark){
            setTheme(R.style.Darkmode)
        }
        else{
            setTheme(R.style.Theme_Animals)
        }
        setContentView(R.layout.activity_setting)

        supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }

        settingsGrid.isChecked = sharedPref.gridLayout
        settingsGrid.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPref.gridLayout = isChecked
        }

        settingsInggris.isChecked = sharedPref.inggris
        settingsInggris.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPref.inggris = isChecked
        }
        darkMode.isChecked = sharedPref.dark
        darkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPref.dark = isChecked
            val intent = Intent(this,SettingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        settingAppName.setText(sharedPref.appName)
        settingAppName.addTextChangedListener {
            sharedPref.appName = it.toString()
        }

        settingColumn.setText(sharedPref.column.toString())
        settingColumn.addTextChangedListener {
            var cols = if (it.toString().length == 0) 1 else it.toString().toInt()
            cols = if (cols > 3) {
                3
            }else if (cols < 1){
                1
            }else{
                cols
            }
            sharedPref.column = cols
        }

        settingsNama.isChecked = sharedPref.nama
        settingsNama.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPref.nama = isChecked
        }

        settingsColor.isChecked = sharedPref.color
        settingsColor.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPref.color = isChecked
        }

    }
// memberikan icon panah kembali buat close activity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}