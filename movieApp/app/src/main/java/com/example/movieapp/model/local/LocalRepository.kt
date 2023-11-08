package com.example.movieapp.model.local

import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.entity.User

interface LocalRepository {
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun getProducts():List<Product>
}