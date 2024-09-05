package com.emirkanmaz.foodcalories.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emirkanmaz.foodcalories.model.Food

@Dao
interface FoodDAO {

    @Query("SELECT * FROM Food")
    suspend fun getAll(): List<Food>

    @Query("SELECT * FROM Food WHERE uuid = :uuid")
    suspend fun findById(uuid: Int): Food

    @Insert
    suspend fun insertAll(vararg food: Food): List<Long>

    @Query("DELETE FROM Food")
    suspend fun deleteAll()

}
