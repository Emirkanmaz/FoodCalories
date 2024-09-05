package com.emirkanmaz.foodcalories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.emirkanmaz.foodcalories.databinding.RecyclerRowBinding
import com.emirkanmaz.foodcalories.model.Food
import com.emirkanmaz.foodcalories.util.buildPlaceHolder
import com.emirkanmaz.foodcalories.util.downloadImage
import com.emirkanmaz.foodcalories.view.FeedFragmentDirections

class FoodRecyclerAdapter(private val foodList: ArrayList<Food>): RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateFoodList(newFoodList: List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.nameTextView.text = foodList[position].foodName
        holder.binding.caloriesTextView.text = foodList[position].foodCalories

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToFoodDetailFragment(foodId = foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.feedImageView.downloadImage(foodList[position].foodImage, buildPlaceHolder(holder.itemView.context))
    }

}