package com.emirkanmaz.foodcalories.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emirkanmaz.foodcalories.model.Food
import kotlin.concurrent.Volatile

@Database(entities = [Food::class], version = 1)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao(): FoodDAO

    companion object {
        @Volatile
        private var instance: FoodDatabase? = null

        operator fun invoke(context: Context): FoodDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java, "food_database"
            ).build()
    }
}
