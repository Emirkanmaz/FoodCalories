package com.emirkanmaz.foodcalories.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emirkanmaz.foodcalories.model.Food
import com.emirkanmaz.foodcalories.roomdb.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): AndroidViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    fun getFoodFromRoom(uuid: Int){
        viewModelScope.launch {
            val food = FoodDatabase(getApplication()).foodDao().findById(uuid)
            foodLiveData.value = food
        }
    }
}