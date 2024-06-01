package com.example.githubuserfika.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.githubuserfika.AppExecutors
import com.example.githubuserfika.SettingPreferences
import com.example.githubuserfika.database.FavoriteRoomDatabase
import com.example.githubuserfika.repository.FavoriteRepository
import com.example.githubuserfika.retrofit.ApiConfig
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "settings"
)
object Injection {
    fun provideRepository(context: Context): FavoriteRepository{
        val apiService = ApiConfig.getApiService()
        val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        val preferences = SettingPreferences.getInstance(context.dataStore)
        return FavoriteRepository.getInstance(apiService, dao, appExecutors, preferences)
    }
}