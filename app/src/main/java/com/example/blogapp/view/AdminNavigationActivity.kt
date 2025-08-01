package com.example.blogapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.view.pages.*
import com.example.blogapp.viewModel.AdminViewModel
import com.example.blogapp.viewModel.ProductViewModel
import com.example.blogapp.viewModel.UserViewModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.repository.UserRepositoryImpl
import com.example.blogapp.view.theme.CraftedTheme

class AdminNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CraftedTheme {
                AdminMainNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminMainNavigation() {
    var selectedIndex by remember { mutableStateOf(0) }
    var showAddProductDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    
    val productViewModel = remember { ProductViewModel(ProductRepositoryImpl()) }
    val userViewModel = remember { UserViewModel(UserRepositoryImpl()) }
    val adminViewModel = remember { AdminViewModel(UserRepositoryImpl()) }
    
    val adminNavItems = listOf(
        AdminNavigationItem(
            icon = Icons.Default.Home,
            label = "Dashboard"
        ),
        AdminNavigationItem(
            icon = Icons.Default.Add,
            label = "Add Product"
        ),
        AdminNavigationItem(
            icon = Icons.Default.List,
            label = "Manage Products"
        ),
        AdminNavigationItem(
            icon = Icons.Default.Person,
            label = "Users"
        ),
        AdminNavigationItem(
            icon = Icons.Default.Settings,
            label = "Settings"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (selectedIndex) {
                            0 -> "Admin Dashboard"
                            1 -> "Add Product"
                            2 -> "Manage Products"
                            3 -> "User Management"
                            4 -> "Admin Settings"
                            else -> "Admin Panel"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color(0xFF8B4513)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF8B4513)
            ) {
                adminNavItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedIndex) {
                0 -> AdminDashboardScreen(
                    productViewModel = productViewModel,
                    userViewModel = userViewModel
                )
                1 -> AddProductScreen(
                    productViewModel = productViewModel,
                    onProductAdded = { showAddProductDialog = false }
                )
                2 -> ManageProductsScreen(productViewModel = productViewModel)
                3 -> AdminUsersScreen(userViewModel = userViewModel)
                4 -> AdminSettingsScreen()
            }
        }
    }
}

data class AdminNavigationItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
) 