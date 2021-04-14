package com.mashudi.animals

import androidx.room.*

@Dao
interface AnimalsDAO {
    @Insert
    fun insert(animals: Animals)

    @Update
    fun update(animals: Animals)

    @Delete
    fun delete(animals: Animals)

    @Query("SELECT * FROM animals")
    fun selectAll() : List<Animals>

    @Query("SELECT * FROM animals WHERE id=:id")
    fun select(id: Int):Animals

}