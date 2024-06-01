package com.example.githubuserfika.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.githubuserfika.databinding.FragmentFollowBinding
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.ui.ViewModelFactory
import com.example.githubuserfika.ui.detail.DetailViewModel


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireContext())
    }
    companion object {
        public const val TAG = "FollowFragment"
        public const val POSITION = "position"
        public const val USERNAME = "rafikaWardah"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = this.arguments?.getInt(POSITION, 1)
        val username = this.arguments?.getString(USERNAME)

            detailViewModel.getListFollowers(username!!)
            detailViewModel.getListFollowing(username!!)

        detailViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            showLoading(isLoading)
        }
        if(position == 1){
            detailViewModel.listFollower.observe(viewLifecycleOwner){ listFollower ->
                setListUser(listFollower)
            }
        }else{
            detailViewModel.listFollowing.observe(viewLifecycleOwner){ listFollowing ->
                setListUser(listFollowing)
            }
        }
    }

    private fun setListUser(itemsItem: List<ItemsItem>){
        val listFollowAdapter = ListFollowAdapter(itemsItem)
        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            rvFollow.adapter = listFollowAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }

    }
}