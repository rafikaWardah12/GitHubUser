package com.example.githubuserfika.ui.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserfika.R
import com.example.githubuserfika.adapter.FavoriteAdapter
import com.example.githubuserfika.databinding.ActivityFavoriteBinding
import com.example.githubuserfika.ui.ViewModelFactory
import com.example.githubuserfika.ui.detail.DetailViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object{
        const val TAG = "favoriteActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        val adapter = FavoriteAdapter()
        binding.rvList.adapter = adapter
        favoriteViewModel.getAllData().observe(this){
            adapter.submitList(it)
        }
    }
}