package com.example.movieapp.model.local

import com.example.movieapp.model.entity.Product
import com.example.movieapp.model.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepositoryImp(private val dp:ProductDatabase):LocalRepository {

    override suspend fun addProduct(product: Product) {
        withContext(Dispatchers.IO){
            dp.productDao().addProduct(product)
        }

    }
    override suspend fun deleteProduct(product: Product) {
        withContext(Dispatchers.IO){
            dp.productDao().deleteProduct(product)
        }
    }

    override suspend fun updateProduct(product: Product){

        withContext(Dispatchers.IO){
            dp.productDao().updateProduct(product)


        }
    }

    override suspend fun getProducts() = withContext(Dispatchers.IO){
        dp.productDao().getProducts()
    }

}