package com.example.movieapp.model.local

import android.content.Context
import android.provider.DocumentsContract.Root
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.entity.User
private const val DATABASE_NAME="my_database"
@Database(entities = [User::class,Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase:RoomDatabase() {
    abstract fun productDao():ProductDAO

    companion object{
        @Volatile
        private var instance:ProductDatabase?=null
        fun getInstance(context:Context):ProductDatabase{
            return instance?: synchronized(Any()){
                instance?:buildDatabase(context).also{ instance = it}
            }
        }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(context.applicationContext,ProductDatabase::class.java,
                DATABASE_NAME).build()

        }
    }
}