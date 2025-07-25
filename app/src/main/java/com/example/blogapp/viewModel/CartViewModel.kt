package com.example.blogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogapp.model.CartItem
import com.example.blogapp.model.ProductModel

class CartViewModel : ViewModel() {
    
    private val _cartItems = MutableLiveData<List<CartItem>>(emptyList())
    val cartItems: LiveData<List<CartItem>> get() = _cartItems
    
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> get() = _totalPrice
    
    fun addToCart(product: ProductModel) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        
        // Check if product already exists in cart
        val existingItem = currentItems.find { it.product.productId == product.productId }
        
        if (existingItem != null) {
            // Update quantity
            existingItem.quantity += 1
        } else {
            // Add new item
            currentItems.add(CartItem(product = product, quantity = 1))
        }
        
        _cartItems.postValue(currentItems)
        updateTotalPrice()
    }
    
    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        currentItems.removeAll { it.product.productId == productId }
        _cartItems.postValue(currentItems)
        updateTotalPrice()
    }
    
    fun updateQuantity(productId: String, quantity: Int) {
        val currentItems = _cartItems.value?.toMutableList() ?: mutableListOf()
        val item = currentItems.find { it.product.productId == productId }
        
        if (item != null) {
            if (quantity <= 0) {
                // Remove item if quantity is 0 or less
                currentItems.remove(item)
            } else {
                item.quantity = quantity
            }
            _cartItems.postValue(currentItems)
            updateTotalPrice()
        }
    }
    
    fun clearCart() {
        _cartItems.postValue(emptyList())
        updateTotalPrice()
    }
    
    private fun updateTotalPrice() {
        val total = _cartItems.value?.sumOf { it.product.productPrice * it.quantity } ?: 0.0
        _totalPrice.postValue(total)
    }
    
    fun getCartItemCount(): Int {
        return _cartItems.value?.sumOf { it.quantity } ?: 0
    }
} 