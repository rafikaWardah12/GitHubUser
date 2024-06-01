package com.example.githubuserfika.repository

import androidx.lifecycle.LiveData
import com.example.githubuserfika.AppExecutors
import com.example.githubuserfika.SettingPreferences
import com.example.githubuserfika.database.FavoriteDao
import com.example.githubuserfika.database.FavoriteUser
import com.example.githubuserfika.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val mFavoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
    private val preferences: SettingPreferences
) {

    fun isFavoriteUser(username: String): LiveData<FavoriteUser> {
        return mFavoriteDao.isFavoriteUser(username)
    }

    fun getAllData(): LiveData<List<FavoriteUser>> {
        return mFavoriteDao.getAllData()
    }

    fun insert(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute {
            mFavoriteDao.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute{
            mFavoriteDao.delete(favoriteUser)
        }
    }

    fun  getThemeSetting(): Flow<Boolean>{
        return preferences.getThemeSetting()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        preferences.saveThemeSetting(isDarkModeActive)
    }
    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors,
            preferences: SettingPreferences
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao, appExecutors, preferences )
            }.also { instance = it }
    }
}