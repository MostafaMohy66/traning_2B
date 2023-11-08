package com.example.movieapp.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.model.entity.Product
import kotlinx.coroutines.selects.select

@Dao
interface ProductDAO {
    @Insert
    suspend fun addProduct(product: Product)
    @Delete
    suspend fun deleteProduct(product: Product)
    @Update
    suspend fun updateProduct(product: Product)
    @Query("select * from product_table")
    suspend fun getProducts():List<Product>
}