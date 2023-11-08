package com.example.movieapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/*
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var productName:String,
    var quantity:Int,
    var productPrice:Int,
    var totalPrice:Double = (productPrice * quantity).toDouble()
)
*/
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var productName: String,
    var quantity: Int,
    var productPrice: Int
): Serializable {
    var totalPrice: Double = (productPrice * quantity).toDouble()

    constructor(id: Int, productName: String, quantity: Int, productPrice: Int, totalPrice: Double) : this( id,productName, quantity, productPrice) {
        this.id=this.id
        this.totalPrice = totalPrice
    }


}
