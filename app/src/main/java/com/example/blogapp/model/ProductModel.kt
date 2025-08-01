package com.example.blogapp.model
import android.net.Uri

data class ProductModel(
    var productId: String = "",
    val productName: String = "",
    val productPrice: Double = 0.0,
    val productDesc: String = "",
    var productImage: String = "",
    val isFeatured: Boolean = false,
    val category: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val originalPrice: Double? = null,
    val discount: Int = 0
)

