package com.example.blogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogapp.model.ProductModel
import com.example.blogapp.repository.ProductRepository

class ProductViewModel(val repo: ProductRepository): ViewModel() {

    fun addProduct( productModel: ProductModel,
                    callback:(Boolean, String)-> Unit
    ){repo.addProduct  (productModel,callback)}

    fun deleteProduct(productId: String,
                      callback: (Boolean, String) -> Unit){
        repo.deleteProduct(productId, callback)
    }

     private val _products= MutableLiveData<ProductModel?>()
    val products: LiveData<ProductModel?> get() = _products

    fun getProductById(productId: String){
        repo.getProductById (productId){ success,message,value ->
            if(success){
                _products.postValue(value)
            }else{_products.postValue(null)}
        }
    }

    private val _allProducts= MutableLiveData<List<ProductModel?>>()
    val allProducts: LiveData<List<ProductModel?>> get() = _allProducts

    private val _featuredProducts = MutableLiveData<List<ProductModel?>>()
    val featuredProducts: LiveData<List<ProductModel?>> get() = _featuredProducts

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private var  _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()

    fun getAllProduct(){
        _loading.postValue(true)

        repo.getAllProduct { success, message, value ->
            if (success) {
                _loading.postValue(false)
                _allProducts.postValue(value)
            } else {
                _loading.postValue(false)
                _allProducts.postValue(emptyList())
            }
        }
    }

    fun getFeaturedProducts() {
        _loading.postValue(true)
        
        repo.getAllProduct { success, message, value ->
            if (success) {
                _loading.postValue(false)
                // Filter featured products
                val featured = value.filter { it?.isFeatured == true }
                _featuredProducts.postValue(value)
            } else {
                _loading.postValue(false)
                _featuredProducts.postValue(emptyList())
            }
        }
    }

    fun getCategories() {
        _loading.postValue(true)
        
        repo.getAllProduct { success, message, value ->
            if (success) {
                _loading.postValue(false)
                // Extract unique categories
                val categories = value
                    .filterNotNull()
                    .map { it.category }
                    .distinct()
                    .filter { it.isNotEmpty() }
                _categories.postValue(categories)
            } else {
                _loading.postValue(false)
                _categories.postValue(emptyList())
            }
        }
    }

    fun updateProduct(productid: String,
                      data: MutableMap<String,Any?>,
                      callback: (Boolean, String) -> Unit){
        repo.updateProduct(productid, data, callback)
    }

    fun uploadImage(context: android.content.Context, imageUri: android.net.Uri, callback: (String?) -> Unit) {
        repo.uploadImage(context, imageUri, callback)
    }

    // Function to populate Firebase with sample featured products
    fun populateSampleData() {
        val sampleProducts = listOf(
            ProductModel(
                productName = "Elegant Ceramic Vase",
                productPrice = 45.0,
                productDesc = "Handcrafted ceramic vase with elegant design",
                productImage = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
                isFeatured = true,
                category = "Pottery",
                rating = 4.5,
                reviewCount = 28
            ),
            ProductModel(
                productName = "Handwoven Basket",
                productPrice = 32.0,
                productDesc = "Beautiful handwoven basket perfect for storage",
                productImage = "https://images.unsplash.com/photo-1582735689369-4fe89db7114c?w=400",
                isFeatured = true,
                category = "Textiles",
                rating = 4.2,
                reviewCount = 15
            ),
            ProductModel(
                productName = "Wooden Cutting Board",
                productPrice = 28.0,
                productDesc = "Hand-carved wooden cutting board",
                productImage = "https://images.unsplash.com/photo-1582735689369-4fe89db7114c?w=400",
                isFeatured = true,
                category = "Woodwork",
                rating = 4.8,
                reviewCount = 42
            ),
            ProductModel(
                productName = "Handmade Necklace",
                productPrice = 65.0,
                productDesc = "Delicate handmade silver necklace",
                productImage = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
                isFeatured = true,
                category = "Jewelry",
                rating = 4.7,
                reviewCount = 8
            ),
            ProductModel(
                productName = "Ceramic Mug Set",
                productPrice = 24.0,
                productDesc = "Set of 4 handcrafted ceramic mugs",
                productImage = "https://images.unsplash.com/photo-1582735689369-4fe89db7114c?w=400",
                isFeatured = false,
                category = "Pottery",
                rating = 4.3,
                reviewCount = 45
            ),
            ProductModel(
                productName = "Woven Wall Hanging",
                productPrice = 89.0,
                productDesc = "Handwoven textile wall art",
                productImage = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
                isFeatured = false,
                category = "Textiles",
                rating = 4.1,
                reviewCount = 12
            )
        )

        sampleProducts.forEach { product ->
            addProduct(product) { success, message ->
                if (success) {
                    println("Sample product added: ${product.productName}")
                } else {
                    println("Failed to add sample product: $message")
                }
            }
        }
    }
}