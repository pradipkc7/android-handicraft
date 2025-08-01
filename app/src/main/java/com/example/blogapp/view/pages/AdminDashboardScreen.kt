package com.example.blogapp.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.viewModel.ProductViewModel
import com.example.blogapp.viewModel.UserViewModel

@Composable
fun AdminDashboardScreen(
    productViewModel: ProductViewModel,
    userViewModel: UserViewModel
) {
    val allProducts = productViewModel.allProducts.observeAsState(initial = emptyList())
    val allUsers = userViewModel.allUsers.observeAsState(initial = emptyList())
    
    LaunchedEffect(Unit) {
        productViewModel.getAllProduct()
        userViewModel.getAllUsers()
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Admin Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )
        }
        
        item {
            Text(
                text = "Welcome back, Admin!",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        
        item {
            // Statistics Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Products",
                    value = "${allProducts.value?.size ?: 0}",
                    icon = Icons.Default.ShoppingCart,
                    modifier = Modifier.weight(1f)
                )
                
                StatCard(
                    title = "Total Users",
                    value = "${allUsers.value?.size ?: 0}",
                    icon = Icons.Default.Person,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Featured Products",
                    value = "${allProducts.value?.count { it?.isFeatured == true } ?: 0}",
                    icon = Icons.Default.Star,
                    modifier = Modifier.weight(1f)
                )
                
                StatCard(
                    title = "Categories",
                    value = "${allProducts.value?.map { it?.category }?.distinct()?.size ?: 0}",
                    icon = Icons.Default.List,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Text(
                text = "Quick Actions",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        
        item {
            QuickActionCard(
                title = "Add New Product",
                description = "Create a new product listing",
                icon = Icons.Default.Add,
                onClick = { /* Navigate to Add Product */ }
            )
        }
        
        item {
            QuickActionCard(
                title = "Manage Products",
                description = "Edit or delete existing products",
                icon = Icons.Default.Edit,
                onClick = { /* Navigate to Manage Products */ }
            )
        }
        
        item {
            QuickActionCard(
                title = "View Users",
                description = "See all registered users",
                icon = Icons.Default.Person,
                onClick = { /* Navigate to Users */ }
            )
        }
        
        item {
            QuickActionCard(
                title = "Database Stats",
                description = "View detailed analytics",
                icon = Icons.Default.Info,
                onClick = { /* Show analytics */ }
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color(0xFF8B4513)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF8B4513)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
} 