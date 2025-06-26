package com.example.blogapp.model

data class ProductModel(
    var  productId: String="",
    val productName: String="",
    val productPrice: Double=0.0,
    val productDesc: String="",
    val image : String? =null,
    val productImage: String? = null // Added for compatibility
)
