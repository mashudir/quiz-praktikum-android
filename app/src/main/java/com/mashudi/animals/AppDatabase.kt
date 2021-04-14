package com.mashudi.animals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

@Database(entities = [Animals::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalsDao() : AnimalsDAO

    companion object{
        private  var instance: AppDatabase?=null

        private  fun populate(db:AppDatabase) {
            val animalsDAO = db.animalsDao()
            AnimalsData.list.forEach {
                animalsDAO.insert(it)
            }
        }

        private val callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    populate(instance as AppDatabase)
                }
            }
        }

        fun getInstance(context: Context) : AppDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "appdb")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance!!
        }
    }
}