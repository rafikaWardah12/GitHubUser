package com.example.githubuserfika.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserfika.di.Injection
import com.example.githubuserfika.repository.FavoriteRepository
import com.example.githubuserfika.ui.detail.DetailViewModel
import com.example.githubuserfika.ui.insert.FavoriteViewModel
import com.example.githubuserfika.ui.list.ListUserViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository)
    : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favoriteRepository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(favoriteRepository) as T
        } else if (modelClass.isAssignableFrom(ListUserViewModel::class.java)){
            return ListUserViewModel(favoriteRepository) as T
        }
        throw  IllegalArgumentException("Unkown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
           instance ?: synchronized(this) {
               instance ?: ViewModelFactory(Injection.provideRepository(context))
           }.also { instance = it }
    }

}