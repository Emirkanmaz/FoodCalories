package com.emirkanmaz.foodcalories.service

import com.emirkanmaz.foodcalories.model.Food
import retrofit2.http.GET

interface FoodAPI {

    //https://raw.githubusercontent.com/Emirkanmaz/FoodCalories/master/JSONDataSet/foods.json
    //BASE URL -> https://raw.githubusercontent.com/
    //ENDPOINT -> Emirkanmaz/FoodCalories/master/JSONDataSet/foods.json

    @GET("Emirkanmaz/FoodCalories/master/JSONDataSet/foods.json")
    suspend fun getFoods(): List<Food>


}