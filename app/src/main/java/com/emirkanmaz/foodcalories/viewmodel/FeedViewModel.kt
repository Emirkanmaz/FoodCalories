package com.emirkanmaz.foodcalories.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emirkanmaz.foodcalories.model.Food
import com.emirkanmaz.foodcalories.roomdb.FoodDatabase
import com.emirkanmaz.foodcalories.service.FoodAPIService
import com.emirkanmaz.foodcalories.util.SelfSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(application: Application): AndroidViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()

    private val foodApiService = FoodAPIService()
    private val selfSharedPreferences = SelfSharedPreferences(getApplication())

    private val dataUpdateTime = 10 * 60 * 1_000_000_000L

    fun refreshData(){
        val dataSaveTime = selfSharedPreferences.getTime()
        if(dataSaveTime != null && dataSaveTime != 0L && System.nanoTime() - dataSaveTime < dataUpdateTime){
            getDataFromRoom()
        } else {
            getDataFromInternet()
        }
    }
    fun refreshDataFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromRoom() {
        foodLoading.value = true

        viewModelScope.launch {
            val foodList = FoodDatabase(getApplication()).foodDao().getAll()
            withContext(Dispatchers.Main){
                showFoods(foodList)
                Toast.makeText(getApplication(), "Data From Room",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDataFromInternet(){
        foodLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val foodList = foodApiService.getData()
            withContext(Dispatchers.Main){
                foodLoading.value = false
                saveToRoom(foodList)
                Toast.makeText(getApplication(), "Data From Internet",Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun showFoods(foodList: List<Food>){
        foods.value = foodList
        foodLoading.value = false
        foodErrorMessage.value = false
    }

    private fun saveToRoom(foodList: List<Food>) {
        viewModelScope.launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAll()
            val uuidList = dao.insertAll(*foodList.toTypedArray())

            for(i in uuidList.indices){
                foodList[i].uuid = uuidList[i].toInt()
            }
            showFoods(foodList)
        }
        selfSharedPreferences.saveTime(System.nanoTime())
    }
}