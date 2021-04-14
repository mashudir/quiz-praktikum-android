package com.mashudi.animals

import android.content.Context
import android.content.SharedPreferences

class SharedPreff(context: Context) {
    private val read: SharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    private val write: SharedPreferences.Editor = read.edit()
    //tipe data any sama dengan object
    //untuk menulis data ke shared preferences
    fun setData(key: String, value: Any?){
        when(value) {
            is Boolean -> write.putBoolean(key, value)
            is Float -> write.putFloat(key, value)
            is Int -> write.putInt(key, value)
            is Long -> write.putLong(key, value)
            is String -> write.putString(key, value)
        }
        //commit ke memori
        //apply ke penyompanan atau storage external

        write.commit()
    }
    //untuk membaca data tertentu
    fun getBoolean(key: String, default: Boolean = false) = read.getBoolean(key, default)
    fun getFloat(key: String, default: Float = 0f) = read.getFloat(key, default)
    fun getInt(key: String, default: Int = 0) = read.getInt(key, default)
    fun getLong(key: String, default: Long = 0) = read.getLong(key, default)
    fun getString(key: String, default: String? = null) = read.getString(key, default)

    //settings apa saja yang akan menggunakan shared preferences
    companion object {
        const val GRID_LAYOUT = "grid_layout"
        const val INGGRIS = "inggris"
        const val APP_NAME = "app_name"
        const val COLUM = "column"
        const val NAMA = "nama"
        const val COLOR = "color"
        const val DARK = "dark"
    }

    var gridLayout: Boolean
        set(value) = setData(GRID_LAYOUT, value)
        get() = getBoolean(GRID_LAYOUT)

    var inggris: Boolean
        set(value) = setData(INGGRIS,value)
        get() = getBoolean(INGGRIS)

    var appName: String?
        set(value) = setData(APP_NAME, value)
        get() = getString(APP_NAME)

    var column: Int
        set(value) = setData(COLUM, value)
        get() = getInt(COLUM)

    var nama: Boolean
        set(value) = setData(NAMA, value)
        get() = getBoolean(NAMA)

    var color: Boolean
        set(value) = setData(COLOR, value)
        get() = getBoolean(COLOR)

    var dark: Boolean
        set(value) = setData(DARK, value)
        get() = getBoolean(DARK)

}