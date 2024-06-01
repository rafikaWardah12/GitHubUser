package com.example.githubuserfika.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserfika.SettingPreferences
import com.example.githubuserfika.repository.FavoriteRepository
import com.example.githubuserfika.response.GitHubResponse
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUserViewModel(private val repository: FavoriteRepository) : ViewModel() {
    private val _userData = MutableLiveData<List<ItemsItem>>()
    val userData: LiveData<List<ItemsItem>> = _userData

    private  val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init{
        findUser("rafika")
    }

    fun  getThemeSetting(): LiveData<Boolean>{
        return repository.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun findUser(q: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearch(q)
        client.enqueue(object : Callback<GitHubResponse> {
                override fun onResponse(
                    call: Call<GitHubResponse>,
                    response: Response<GitHubResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _userData.value = response.body()?.items
                    } else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )
    }
}