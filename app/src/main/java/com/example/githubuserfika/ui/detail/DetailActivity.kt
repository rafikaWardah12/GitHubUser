package com.example.githubuserfika.ui.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.example.githubuserfika.R
import com.example.githubuserfika.database.FavoriteUser
import com.example.githubuserfika.databinding.ActivityDetailBinding
import com.example.githubuserfika.ui.ViewModelFactory
import com.example.githubuserfika.ui.follow.SectionPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }
        companion object {
        const val TAG = "DetailActivity"
        const val USERNAME = "rafikaWardah"
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

       val username = intent.getStringExtra(USERNAME)
        if(username != null){
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userData.observe(this){userData ->
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(userData.avatarUrl)
                    .into(imgProfile)
                tvItemUsername.text = userData.login
                tvItemName.text = userData.name

                val follow = mutableListOf<String>(
                    String.format(getString(R.string.follower), userData.followers),
                    String.format(getString(R.string.following), userData.following)
                )

                val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
                sectionPagerAdapter.username = userData.login.toString()
                viewPager.adapter = sectionPagerAdapter
                TabLayoutMediator(tab, viewPager) { tab, position ->
                    tab.text = follow[position]
                }.attach()

                //Mengambil data dari detail view model
                detailViewModel.getFavoriteUser(userData.login as String).observe(this@DetailActivity){ favUser ->
                    //Jika favorite user belum ada di database
                    if(favUser == null){
                        fillableColor(false)
                        binding.favAdd.setOnClickListener{
                            val favoriteUser = FavoriteUser(userData.login, userData.avatarUrl)
                            detailViewModel.insertUser(favoriteUser)
                        }
                        //Jika user ada di database
                    }else{
                        fillableColor(true)
                        binding.favAdd.setOnClickListener{
                            detailViewModel.deleteUser(favUser)
                        }
                    }
                }

            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility= View.GONE
        }
    }

    private fun fillableColor(isFavoriteUser: Boolean){
        if(isFavoriteUser){
            binding.favAdd.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_favorite_24))
        }else{
            binding.favAdd.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, R.drawable.baseline_favorite_border_24))
        }
    }
}

