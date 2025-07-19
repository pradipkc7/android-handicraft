package com.example.blogapp.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.model.ProductModel
import com.example.blogapp.model.UserModel
import com.example.blogapp.viewModel.ProductViewModel
import com.example.blogapp.viewModel.UserViewModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.repository.UserRepositoryImpl

@Composable
fun AdminScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    
    val productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ProductViewModel(ProductRepositoryImpl()) as T
            }
        }
    )
    
    val userViewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(UserRepositoryImpl()) as T
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Admin Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = Color(0xFF8B4513)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Products") },
                icon = { Icon(Icons.Default.List, contentDescription = "Products") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Users") },
                icon = { Icon(Icons.Default.Person, contentDescription = "Users") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (selectedTab) {
            0 -> ProductsManagementTab(productViewModel = productViewModel)
            1 -> UsersManagementTab(userViewModel = userViewModel)
        }
    }
}

@Composable
fun ProductsManagementTab(productViewModel: ProductViewModel) {
    val allProducts = productViewModel.allProducts.observeAsState(initial = emptyList())
    val loading = productViewModel.loading.observeAsState(initial = false)
    var showAddProductDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productViewModel.getAllProduct()
    }

    Column {
        // Add Product Button
        Button(
            onClick = { showAddProductDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4513))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add New Product")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF8B4513))
            }
        } else if (allProducts.value.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No products available", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allProducts.value?.filterNotNull() ?: emptyList()) { product ->
                    AdminProductCard(
                        product = product,
                        onDelete = { productViewModel.deleteProduct(product.productId) { _, _ -> } }
                    )
                }
            }
        }
    }

    if (showAddProductDialog) {
        AddProductDialog(
            onDismiss = { showAddProductDialog = false },
            onProductAdded = { 
                showAddProductDialog = false
                productViewModel.getAllProduct()
            },
            productViewModel = productViewModel
        )
    }
}

@Composable
fun UsersManagementTab(userViewModel: UserViewModel) {
    val allUsers = userViewModel.allUsers.observeAsState(initial = emptyList())
    val loading = userViewModel.loading.observeAsState(initial = false)

    LaunchedEffect(Unit) {
        userViewModel.getAllUsers()
    }

    Column {
        Text(
            text = "User Management",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF8B4513))
            }
        } else if (allUsers.value.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("No users available", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allUsers.value?.filterNotNull() ?: emptyList()) { user ->
                    AdminUserCard(user = user)
                }
            }
        }
    }
}

@Composable
fun AdminProductCard(
    product: ProductModel,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            if (product.productImage.isNotEmpty()) {
                AsyncImage(
                    model = product.productImage,
                    contentDescription = product.productName,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.craft)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.craft),
                    contentDescription = product.productName,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "$${product.productPrice}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Category: ${product.category}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                if (product.isFeatured) {
                    Text(
                        text = "Featured",
                        fontSize = 12.sp,
                        color = Color(0xFF8B4513),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Delete Button
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun AdminUserCard(user: UserModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF8B4513), RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.firstName.firstOrNull()?.uppercase() ?: "U",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // User Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}".trim().ifEmpty { "Unknown User" },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = user.email.ifEmpty { "No email" },
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "ID: ${user.userId}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onProductAdded: () -> Unit,
    productViewModel: ProductViewModel
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productDesc by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var isFeatured by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Product") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = productDesc,
                    onValueChange = { productDesc = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                OutlinedTextField(
                    value = productCategory,
                    onValueChange = { productCategory = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isFeatured,
                        onCheckedChange = { isFeatured = it }
                    )
                    Text("Featured Product")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (productName.isNotEmpty() && productPrice.isNotEmpty()) {
                        isLoading = true
                        val price = productPrice.toDoubleOrNull() ?: 0.0
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
                                onProductAdded()
                            }
                        }
                    }
                },
                enabled = !isLoading && productName.isNotEmpty() && productPrice.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White
                    )
                } else {
                    Text("Add Product")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 