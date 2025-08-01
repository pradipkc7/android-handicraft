package com.example.blogapp.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import com.example.blogapp.model.UserModel
import com.example.blogapp.viewModel.ProductViewModel
import com.example.blogapp.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    productViewModel: ProductViewModel,
    userViewModel: UserViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    val tabTitles = listOf("Products", "Users")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Text(
            text = "Admin Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Tab Content
        when (selectedTabIndex) {
            0 -> ProductsManagementTab(productViewModel)
            1 -> UsersManagementTab(userViewModel)
        }
    }
}

@Composable
fun ProductsManagementTab(productViewModel: ProductViewModel) {
    val allProducts = productViewModel.allProducts.observeAsState(initial = emptyList())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Add Product Button
        Button(
            onClick = { /* TODO: Show add product dialog */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8B4513)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add New Product")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Products List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allProducts.value?.filterNotNull() ?: emptyList()) { product ->
                ProductCard(
                    product = product,
                    onDelete = { productId ->
                        productViewModel.deleteProduct(productId) { success, message ->
                            if (success) {
                                productViewModel.getAllProduct()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun UsersManagementTab(userViewModel: UserViewModel) {
    val allUsers = userViewModel.allUsers.observeAsState(initial = emptyList())
    
    LaunchedEffect(Unit) {
        userViewModel.getAllUsers()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Users",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(allUsers.value?.filterNotNull() ?: emptyList()) { user ->
                UserCard(user = user)
            }
        }
    }
}

@Composable
fun ProductCard(
    product: com.example.blogapp.model.ProductModel,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Placeholder
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray, CircleShape)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
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
            }
            
            // Delete Button
            IconButton(
                onClick = { onDelete(product.productId) }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Product",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun UserCard(user: UserModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF8B4513), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${user.firstName.firstOrNull()?.uppercase() ?: ""}${user.lastName.firstOrNull()?.uppercase() ?: ""}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // User Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = user.email,
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