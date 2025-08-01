package com.example.blogapp.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.model.ProductModel
import com.example.blogapp.utils.ImageUtils
import com.example.blogapp.viewModel.ProductViewModel
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    productViewModel: ProductViewModel,
    onProductAdded: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var isFeatured by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Add New Product",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Product Image Upload
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable {
                    // Launch image picker
                    val imageUtils = ImageUtils(context as android.app.Activity, context as androidx.activity.result.ActivityResultRegistryOwner)
                    imageUtils.registerLaunchers { uri ->
                        selectedImageUri = uri
                    }
                    imageUtils.launchImagePicker()
                },
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    selectedImageUri != null -> {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Image",
                                modifier = Modifier.size(48.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Click to add product image",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Product Name
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Product Name")
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Product Price
        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Price")
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Product Description
        OutlinedTextField(
            value = productDesc,
            onValueChange = { productDesc = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5,
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "Description")
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Product Category
        OutlinedTextField(
            value = productCategory,
            onValueChange = { productCategory = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.List, contentDescription = "Category")
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Featured Product Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFeatured,
                onCheckedChange = { isFeatured = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF8B4513)
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "Featured Product",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Add Product Button
        Button(
            onClick = {
                if (productName.isNotEmpty() && productPrice.isNotEmpty()) {
                    isLoading = true
                    val price = productPrice.toDoubleOrNull() ?: 0.0
                    
                    // If image is selected, upload it first
                    if (selectedImageUri != null) {
                        productViewModel.uploadImage(context, selectedImageUri!!) { imageUrl ->
                            if (imageUrl != null) {
                                val newProduct = ProductModel(
                                    productName = productName,
                                    productPrice = price,
                                    productDesc = productDesc,
                                    productImage = imageUrl,
                                    category = productCategory,
                                    isFeatured = isFeatured,
                                    rating = 0.0,
                                    reviewCount = 0
                                )
                                
                                productViewModel.addProduct(newProduct) { success, message ->
                                    isLoading = false
                                    if (success) {
                                        showSuccessDialog = true
                                        // Reset form
                                        productName = ""
                                        productPrice = ""
                                        productDesc = ""
                                        productCategory = ""
                                        isFeatured = false
                                        selectedImageUri = null
                                    }
                                }
                            } else {
                                isLoading = false
                            }
                        }
                    } else {
                        // No image selected, add product without image
                        val newProduct = ProductModel(
                            productName = productName,
                            productPrice = price,
                            productDesc = productDesc,
                            productImage = "",
                            category = productCategory,
                            isFeatured = isFeatured,
                            rating = 0.0,
                            reviewCount = 0
                        )
                        
                        productViewModel.addProduct(newProduct) { success, message ->
                            isLoading = false
                            if (success) {
                                showSuccessDialog = true
                                // Reset form
                                productName = ""
                                productPrice = ""
                                productDesc = ""
                                productCategory = ""
                                isFeatured = false
                                selectedImageUri = null
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && productName.isNotEmpty() && productPrice.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B4513)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White
                )
            } else {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Product",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Product")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Preview Card
        if (productName.isNotEmpty() || productPrice.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Preview",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (productName.isNotEmpty()) {
                        Text(
                            text = "Name: $productName",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    
                    if (productPrice.isNotEmpty()) {
                        Text(
                            text = "Price: $$productPrice",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    
                    if (productCategory.isNotEmpty()) {
                        Text(
                            text = "Category: $productCategory",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    
                    if (isFeatured) {
                        Text(
                            text = "Featured: Yes",
                            fontSize = 14.sp,
                            color = Color(0xFF8B4513),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    if (selectedImageUri != null) {
                        Text(
                            text = "Image: Selected",
                            fontSize = 14.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    
    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success!") },
            text = { Text("Product has been added successfully to the database.") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showSuccessDialog = false
                        onProductAdded()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
} 