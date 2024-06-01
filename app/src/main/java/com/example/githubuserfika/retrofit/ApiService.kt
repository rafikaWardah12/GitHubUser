package com.example.githubuserfika.retrofit
import com.example.githubuserfika.response.DetailUserResponse
import com.example.githubuserfika.response.GitHubResponse
import com.example.githubuserfika.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearch(
        @Query("q") q: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getListFollower(
        @Path("username") username: String
    ):Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}