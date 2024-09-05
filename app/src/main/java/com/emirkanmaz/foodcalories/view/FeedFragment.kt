package com.emirkanmaz.foodcalories.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirkanmaz.foodcalories.adapter.FoodRecyclerAdapter
import com.emirkanmaz.foodcalories.databinding.FragmentFeedBinding
import com.emirkanmaz.foodcalories.viewmodel.FeedViewModel
import java.util.ArrayList

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeedViewModel
    private var adapter = FoodRecyclerAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        viewModel.refreshData()


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.errorMessageTextView.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner){
            adapter.updateFoodList(it)
            binding.recyclerView.visibility = View.VISIBLE
        }
        viewModel.foodErrorMessage.observe(viewLifecycleOwner){
            if (it){
                binding.errorMessageTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.errorMessageTextView.visibility = View.GONE
            }
        }
        viewModel.foodLoading.observe(viewLifecycleOwner){
            if (it){
                binding.errorMessageTextView.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}