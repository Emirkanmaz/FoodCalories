package com.emirkanmaz.foodcalories.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.emirkanmaz.foodcalories.databinding.FragmentFoodDetailBinding
import com.emirkanmaz.foodcalories.util.buildPlaceHolder
import com.emirkanmaz.foodcalories.util.downloadImage
import com.emirkanmaz.foodcalories.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodDetailViewModel
    private var foodId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]

        arguments?.let {
            foodId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }
        viewModel.getFoodFromRoom(foodId)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            binding.imageView.downloadImage(it.foodImage, buildPlaceHolder(requireContext()))
            binding.nameTextView.text = it.foodName
            binding.fatTextView.text = it.foodFat
            binding.caloriesTextView.text = it.foodCalories
            binding.proteinTextView.text = it.foodProtein
            binding.carbohydratesTextView.text = it.foodCarbohydrates
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}