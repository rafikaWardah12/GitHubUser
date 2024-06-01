package com.example.githubuserfika.ui.insert

import androidx.lifecycle.ViewModel
import com.example.githubuserfika.database.FavoriteUser
import com.example.githubuserfika.repository.FavoriteRepository

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    fun getAllData() = favoriteRepository.getAllData()

    companion object{
        private const val TAG = "FavoriteViewModel"
        private const val USERNAME = "rafikaWardah"
    }
}