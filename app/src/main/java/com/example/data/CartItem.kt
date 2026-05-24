package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val productName: String,
    val productCategory: String,
    val productPrice: Double,
    val selectedSize: String,
    val selectedColor: String,
    val quantity: Int
)
