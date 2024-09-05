package com.emirkanmaz.foodcalories.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Food(
    @ColumnInfo("name")
    @SerializedName("name")
    val foodName: String?,

    @ColumnInfo("calories")
    @SerializedName("calories")
    val foodCalories: String?,

    @ColumnInfo("carbohydrates")
    @SerializedName("carbohydrates")
    val foodCarbohydrates: String?,

    @ColumnInfo("protein")
    @SerializedName("protein")
    val foodProtein: String?,

    @ColumnInfo("fat")
    @SerializedName("fat")
    val foodFat: String?,

    @ColumnInfo("image")
    @SerializedName("image")
    val foodImage: String?,
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}
