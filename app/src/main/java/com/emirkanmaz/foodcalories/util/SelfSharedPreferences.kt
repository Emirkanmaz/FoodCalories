package com.emirkanmaz.foodcalories.util

import android.content.Context
import android.content.SharedPreferences

class SelfSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: SelfSharedPreferences? = null

        operator fun invoke(context: Context): SelfSharedPreferences {
            return instance ?: synchronized(this) {
                instance ?: buildSelfSharedPreferences(context).also {
                    instance = it
                }
            }
        }

        private fun buildSelfSharedPreferences(context: Context): SelfSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SelfSharedPreferences()
        }
    }
    fun saveTime(time: Long) {
        sharedPreferences?.edit()?.putLong(TIME, time)?.apply()
    }

    fun getTime(): Long {
        return sharedPreferences?.getLong(TIME, 0L) ?: 0L
    }
}