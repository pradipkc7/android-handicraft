package com.example.blogapp.repository

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.blogapp.model.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

class ProductRepositoryImpl: ProductRepository{
    val  database: FirebaseDatabase= FirebaseDatabase.getInstance()
    val ref: DatabaseReference =database.reference.child("product")

    private val cloudinary =Cloudinary(
        mapOf(
            "cloud_name" to "dxwr7qza9",
            "api_key" to "545811657876156",
            "api_secret" to "Oo50NMS-vrURt3gETED4ibe21uo"
        )
    )

    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                // ✅ Fix: Remove extensions from file name before upload
                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                // ✅ Run UI updates on the Main Thread
                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }
    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    override fun addProduct(
        productModel: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        val id=ref.push().key.toString()
        productModel.productId = id
       ref.child(productModel.productId).setValue(productModel).addOnCompleteListener {
           if(it.isSuccessful){
               callback(true,"Product added successfully")
           }else{
               callback(false,"${it.exception?.message}")
           }
       }
    }

    override fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Product deleted successfully")
            } else {
                callback(false, it.exception?.message ?: "Failed to delete product")
            }
        }
    }

    override fun getProductById(
        productId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    ) {
        ref.child(productId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   var products=snapshot.getValue(ProductModel::class.java)
                   if (products !=null){
                       callback(true,"Product fetched",products)
                   }
               }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false,error.message,null)
            }
        })
    }

    override fun getAllProduct(callback: (Boolean, String, List<ProductModel?>) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val allProducts = mutableListOf<ProductModel>()
                    for (eachProduct in snapshot.children) {
                        val product = eachProduct.getValue(ProductModel::class.java)
                        if (product != null) {
                            allProducts.add(product)
                        }
                    }
                    callback(true, "Product fetched", allProducts)
                } else {
                    callback(true, "No products found", emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message, emptyList())
            }
        })
    }

    override fun updateProduct(
        productid: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(productid).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true,"Product updated successfully")
            }else{callback(false,"${it.exception?.message}")
            }
        }
    }

//    override fun uploadImage(
//        context: Context,
//        imageUri: Uri,
//        callback: (String?) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getFileNameFromUri(
//        context: Context,
//        uri: Uri
//    ): String? {
//        TODO("Not yet implemented")
//    }
}