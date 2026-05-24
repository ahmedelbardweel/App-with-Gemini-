package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    DETAIL,
    CART,
    CHECKOUT,
    TRACKING
}

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val repository = ShopRepository(database.cartDao(), database.orderDao())

    // Navigation state
    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // Search and filter states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("الكل") // "الكل", "رجال", "نساء", "أطفال"
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Cart and Orders lists
    val cartItems: StateFlow<List<CartItem>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders: StateFlow<List<Order>> = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Product size & color selection in details screen
    private val _selectedSize = MutableStateFlow("")
    val selectedSize: StateFlow<String> = _selectedSize.asStateFlow()

    private val _selectedColor = MutableStateFlow("")
    val selectedColor: StateFlow<String> = _selectedColor.asStateFlow()

    private val _detailQuantity = MutableStateFlow(1)
    val detailQuantity: StateFlow<Int> = _detailQuantity.asStateFlow()

    // Checkout form states
    private val _checkoutName = MutableStateFlow("")
    val checkoutName: StateFlow<String> = _checkoutName.asStateFlow()

    private val _checkoutPhone = MutableStateFlow("")
    val checkoutPhone: StateFlow<String> = _checkoutPhone.asStateFlow()

    private val _checkoutAddress = MutableStateFlow("")
    val checkoutAddress: StateFlow<String> = _checkoutAddress.asStateFlow()

    // Credit Card info
    private val _cardNumber = MutableStateFlow("")
    val cardNumber: StateFlow<String> = _cardNumber.asStateFlow()

    private val _cardExpiry = MutableStateFlow("")
    val cardExpiry: StateFlow<String> = _cardExpiry.asStateFlow()

    private val _cardCvv = MutableStateFlow("")
    val cardCvv: StateFlow<String> = _cardCvv.asStateFlow()

    // Payment state
    private val _paymentProcessing = MutableStateFlow(false)
    val paymentProcessing: StateFlow<Boolean> = _paymentProcessing.asStateFlow()

    private val _paymentSuccess = MutableStateFlow(false)
    val paymentSuccess: StateFlow<Boolean> = _paymentSuccess.asStateFlow()

    private val _recentlyPlacedOrder = MutableStateFlow<Order?>(null)
    val recentlyPlacedOrder: StateFlow<Order?> = _recentlyPlacedOrder.asStateFlow()

    // Backstack for manual navigation history
    private val _backstack = MutableStateFlow<List<Screen>>(emptyList())
    val canGoBack: StateFlow<Boolean> = _backstack
        .map { it.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        // Pre-insert a simulation order so first-time users can see how shipment tracking looks!
        viewModelScope.launch {
            repository.orders.first().let { currentOrders ->
                if (currentOrders.isEmpty()) {
                    repository.createOrder(
                        summary = "1x عباءة مخملية مطرزة بالذهب، 1x ثوب سعودي كلاسيك",
                        total = 570.00,
                        address = "المملكة العربية السعودية، الرياض، شارع التحلية حي العليا",
                        recipientName = "أحمد البردويلي",
                        recipientPhone = "+966 50 123 4567",
                        paymentMethod = "بطاقة ائتمان مدي الائمنة"
                    ).also { firstOrder ->
                        // Put it into a "SHIPPED" state for varied visual initial states
                        repository.updateOrderStatus(firstOrder.id, "SHIPPED")
                    }
                }
            }
        }
    }

    // Navigation operations
    fun navigateTo(screen: Screen) {
        _backstack.value = _backstack.value + _currentScreen.value
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        val currentList = _backstack.value
        if (currentList.isNotEmpty()) {
            val updatedList = currentList.toMutableList()
            val previousScreen = updatedList.removeAt(updatedList.size - 1)
            _backstack.value = updatedList
            _currentScreen.value = previousScreen
            return true
        }
        return false
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
        _selectedSize.value = product.sizes.firstOrNull() ?: ""
        _selectedColor.value = product.colors.firstOrNull() ?: ""
        _detailQuantity.value = 1
        navigateTo(Screen.DETAIL)
    }

    fun setSize(size: String) { _selectedSize.value = size }
    fun setColor(color: String) { _selectedColor.value = color }
    fun incrementDetailQty() { _detailQuantity.value += 1 }
    fun decrementDetailQty() { if (_detailQuantity.value > 1) _detailQuantity.value -= 1 }

    fun setSearchQuery(query: String) { _searchQuery.value = query }
    fun setCategory(category: String) { _selectedCategory.value = category }

    // Checkout Form modifiers
    fun setCheckoutName(value: String) { _checkoutName.value = value }
    fun setCheckoutPhone(value: String) { _checkoutPhone.value = value }
    fun setCheckoutAddress(value: String) { _checkoutAddress.value = value }
    fun setCardNumber(value: String) { _cardNumber.value = value.trim().take(19) } // Limit typical card input format
    fun setCardExpiry(value: String) { _cardExpiry.value = value.trim().take(5) } // MM/YY
    fun setCardCvv(value: String) { _cardCvv.value = value.trim().filter { it.isDigit() }.take(4) }

    // Cart actions
    fun addSelectedProductToCart() {
        val product = _selectedProduct.value ?: return
        viewModelScope.launch {
            repository.addToCart(
                productId = product.id,
                name = product.name,
                category = product.category,
                price = product.price,
                size = _selectedSize.value,
                color = _selectedColor.value,
                quantity = _detailQuantity.value
            )
            navigateTo(Screen.CART)
        }
    }

    fun updateCartQty(cartId: Int, newQty: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(cartId, newQty)
        }
    }

    fun deleteCartItem(item: CartItem) {
        viewModelScope.launch {
            repository.deleteFromCart(item)
        }
    }

    // Checkout processing with simulated heavy security and confirmation delays!
    fun processSecurePayment() {
        if (_checkoutName.value.isBlank() || _checkoutPhone.value.isBlank() ||
            _checkoutAddress.value.isBlank() || _cardNumber.value.replace(" ", "").length < 15 ||
            _cardExpiry.value.length < 5 || _cardCvv.value.length < 3
        ) return

        viewModelScope.launch {
            _paymentProcessing.value = true
            _paymentSuccess.value = false
            
            // Heavy cryptography & security handshake simulation (2.3 seconds)
            delay(2300)
            
            val cartList = cartItems.value
            if (cartList.isNotEmpty()) {
                val summary = cartList.joinToString("، ") { "${it.quantity}x ${it.productName}" }
                val total = cartList.sumOf { it.productPrice * it.quantity }
                
                val order = repository.createOrder(
                    summary = summary,
                    total = total + 20.0, // base price + shipping (20 SAR)
                    address = _checkoutAddress.value,
                    recipientName = _checkoutName.value,
                    recipientPhone = _checkoutPhone.value,
                    paymentMethod = "بطاقة مدى الائتمانية الآمنة (SSL)"
                )
                
                _recentlyPlacedOrder.value = order
                _paymentProcessing.value = false
                _paymentSuccess.value = true
                
                // Clear the form fields
                _checkoutName.value = ""
                _checkoutPhone.value = ""
                _checkoutAddress.value = ""
                _cardNumber.value = ""
                _cardExpiry.value = ""
                _cardCvv.value = ""

                // Start automatic tracking progression background coroutine for this specific order!
                // This advances the states of order simulation over minutes:
                // PROCESSING -> SHIPPED -> IN_TRANSIT -> DELIVERED
                startAutomaticTrackingSimulation(order.id)
            } else {
                _paymentProcessing.value = false
            }
        }
    }

    fun acknowledgePaymentSuccess() {
        _paymentSuccess.value = false
        _recentlyPlacedOrder.value = null
        navigateTo(Screen.TRACKING)
    }

    // Live Shipment status stimulation for testing the tracking pipeline!
    private fun startAutomaticTrackingSimulation(orderId: String) {
        viewModelScope.launch {
            // Wait 25 seconds for PROCESSING -> SHIPPED
            delay(25000)
            repository.updateOrderStatus(orderId, "SHIPPED")
            
            // Wait another 30 seconds for SHIPPED -> IN_TRANSIT
            delay(30000)
            repository.updateOrderStatus(orderId, "IN_TRANSIT")
            
            // Wait another 40 seconds for IN_TRANSIT -> DELIVERED
            delay(40000)
            repository.updateOrderStatus(orderId, "DELIVERED")
        }
    }

    // Forceful status progression for manual testing / demo purposes. Awesome!
    fun simulateNextTrackingState(order: Order) {
        viewModelScope.launch {
            val nextState = when (order.status) {
                "PROCESSING" -> "SHIPPED"
                "SHIPPED" -> "IN_TRANSIT"
                "IN_TRANSIT" -> "DELIVERED"
                else -> "PROCESSING" // Loop back to restart test
            }
            repository.updateOrderStatus(order.id, nextState)
        }
    }
}
