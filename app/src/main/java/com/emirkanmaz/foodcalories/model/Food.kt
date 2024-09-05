package com.emirkanmaz.foodcalories.model

import com.google.gson.annotations.SerializedName

data class Food(
    val name: String?,
    val calories: String?,
    val carbohydrates: String?,
    val protein: String?,
    val fat: String?,
    val image: String?,
)
