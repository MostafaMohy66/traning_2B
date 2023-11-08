package com.example.movieapp.model.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.movieapp.model.entity.User

@Dao
interface UserDAO {

    @Insert
    suspend fun addUser(user: User)
}