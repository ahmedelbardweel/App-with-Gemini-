package com.example.data

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class ShopRepository(
    private val cartDao: CartDao,
    private val orderDao: OrderDao
) {
    val cartItems: Flow<List<CartItem>> = cartDao.getCartItems()
    val orders: Flow<List<Order>> = orderDao.getOrders()

    suspend fun addToCart(
        productId: Int,
        name: String,
        category: String,
        price: Double,
        size: String,
        color: String,
        quantity: Int
    ) {
        val existingItem = cartDao.getCartItemByDetails(productId, size, color)
        if (existingItem != null) {
            cartDao.updateQuantity(existingItem.id, existingItem.quantity + quantity)
        } else {
            cartDao.insertCartItem(
                CartItem(
                    productId = productId,
                    productName = name,
                    productCategory = category,
                    productPrice = price,
                    selectedSize = size,
                    selectedColor = color,
                    quantity = quantity
                )
            )
        }
    }

    suspend fun updateCartQuantity(id: Int, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteCartItemById(id)
        } else {
            cartDao.updateQuantity(id, quantity)
        }
    }

    suspend fun deleteFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun deleteFromCartById(id: Int) {
        cartDao.deleteCartItemById(id)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun createOrder(
        summary: String,
        total: Double,
        address: String,
        recipientName: String,
        recipientPhone: String,
        paymentMethod: String
    ): Order {
        val orderId = "AN-${(10000..99999).random()}"
        val trackingNo = "TRK${(100000000..999999999).random()}"
        val newOrder = Order(
            id = orderId,
            orderDate = System.currentTimeMillis(),
            summary = summary,
            totalAmount = total,
            shippingAddress = address,
            recipientName = recipientName,
            recipientPhone = recipientPhone,
            paymentMethod = paymentMethod,
            status = "PROCESSING", // Steps: PROCESSING -> SHIPPED -> IN_TRANSIT -> DELIVERED
            trackingNumber = trackingNo
        )
        orderDao.insertOrder(newOrder)
        cartDao.clearCart()
        return newOrder
    }

    suspend fun updateOrderStatus(orderId: String, status: String) {
        orderDao.updateOrderStatus(orderId, status)
    }

    suspend fun getOrderById(orderId: String): Order? {
        return orderDao.getOrderById(orderId)
    }

    suspend fun clearAllOrders() {
        orderDao.clearOrders()
    }
}
