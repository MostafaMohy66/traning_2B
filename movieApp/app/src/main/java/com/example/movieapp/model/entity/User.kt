package com.example.movieapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
   @PrimaryKey(autoGenerate = true)
    var id:Int,
    var userName:String,
    var email:String,
    var phone:String,
    var password:String

)
