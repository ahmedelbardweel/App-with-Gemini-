package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: String, // e.g. "AN-10294"
    val orderDate: Long,
    val summary: String, // e.g. "2x ثوب سعودي، 1x بشت حساوي"
    val totalAmount: Double,
    val shippingAddress: String,
    val recipientName: String,
    val recipientPhone: String,
    val paymentMethod: String, // e.g. "مدى / بطاقة ائتمان"
    val status: String, // "PROCESSING", "SHIPPED", "IN_TRANSIT", "DELIVERED"
    val trackingNumber: String // e.g. "TRK94829348"
)
