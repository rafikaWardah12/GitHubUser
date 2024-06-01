package com.example.githubuserfika.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserfika.database.FavoriteUser
import com.example.githubuserfika.repository.FavoriteRepository
import com.example.githubuserfika.response.DetailUserResponse
import com.example.githubuserfika.response.GitHubResponse
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.retrofit.ApiConfig
import com.example.githubuserfika.ui.follow.FollowFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val _userData = MutableLiveData<DetailUserResponse>()
    val userData: LiveData<DetailUserResponse> = _userData

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"
        private const val USERNAME = "rafikaWardah"
    }

    fun getDetailUser(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(q)
        client.enqueue(object : Callback<DetailUserResponse> {

            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _userData.postValue(response.body())
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")

                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListFollowers(q: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollower(q)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    if(response.body() != null) {
                        _listFollower.value = response.body()
                    }
                }else{
                    Log.e(FollowFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowFragment.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListFollowing(q: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(q)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    if(response.body() != null){
                        _listFollowing.value = response.body()
                    }
                }else{
                    Log.e(FollowFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowFragment.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFavoriteUser(username: String) = favoriteRepository.isFavoriteUser(username)

    fun insertUser(favoriteUser: FavoriteUser) {
        favoriteRepository.insert(favoriteUser)
    }

    fun deleteUser(favoriteUser: FavoriteUser) {
        favoriteRepository.delete(favoriteUser)
    }

}