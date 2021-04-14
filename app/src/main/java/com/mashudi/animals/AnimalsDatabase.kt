package com.mashudi.animals

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Animals::class],version = 1)
abstract class AnimalsDatabase : RoomDatabase() {
    abstract fun animalsDao() : AnimalsDAO

    companion object {
        private var instance: AnimalsDatabase? = null
        private  val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
        private fun populate(db:AnimalsDatabase) {
            val animalsDao = db.animalsDao()
        }
    }
}