package com.example.githubuserfika.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

    @Dao
    interface FavoriteDao{
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        fun insert(favoriteUser: FavoriteUser)

        @Delete
        fun delete(favoriteUser: FavoriteUser)

        @Query("SELECT * FROM FavoriteUser")
        fun getAllData(): LiveData<List<FavoriteUser>>

        @Query("SELECT * FROM FavoriteUser WHERE username = :username")
        fun isFavoriteUser(username: String) : LiveData<FavoriteUser>

    }