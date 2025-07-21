package com.example.blogapp.repository
import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarData
import com.example.blogapp.model.ProductModel
interface ProductRepository {
    fun addProduct( productModel: ProductModel,
                callback:(Boolean, String)-> Unit
    )

    fun deleteProduct(productId: String,
               callback: (Boolean, String) -> Unit)

    // euta product chaiye ma getById use garne

    fun getProductById(productId: String,callback: (Boolean, String, ProductModel?) -> Unit)

    //sabbai product chaiye ma list bata approach hanne


    fun getAllProduct( callback: (Boolean, String, List<ProductModel?>) -> Unit)

    fun updateProduct(productid: String,
                      data: MutableMap<String,Any?>,
                      callback: (Boolean, String) -> Unit)
    fun uploadImage(context: Context,imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context,uri: Uri): String?


}
