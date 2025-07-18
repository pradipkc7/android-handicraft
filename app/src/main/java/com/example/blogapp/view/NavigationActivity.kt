package com.example.blogapp.view

import android.app.Activity
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blogapp.model.ProductModel
import com.example.blogapp.view.pages.*
import com.example.blogapp.view.theme.CraftedTheme
import com.example.blogapp.viewModel.CartViewModel
import com.example.blogapp.viewModel.AdminViewModel
import com.example.blogapp.repository.UserRepositoryImpl

class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CraftedTheme {
                MainNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    var selectedIndex by remember { mutableStateOf(0) }
    var showOrdersScreen by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<ProductModel?>(null) }
    var showAdminLogin by remember { mutableStateOf(false) }
    
    val cartViewModel: CartViewModel = viewModel()
    val adminViewModel: AdminViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return AdminViewModel(UserRepositoryImpl()) as T
            }
        }
    )

    // Check if coming from admin login
    val context = LocalContext.current as Activity
    LaunchedEffect(Unit) {
        val isAdmin = context.intent.getBooleanExtra("isAdmin", false)
        if (isAdmin) {
            adminViewModel.checkAdminAccess("admin", "admin123")
        }
    }

    val bottomNavItems = listOf(
        BottomNavigationItem(
            icon = Icons.Default.Home,
            label = "Home"
        ),
        BottomNavigationItem(
            icon = Icons.Default.List,
            label = "Categories"
        ),
        BottomNavigationItem(
            icon = Icons.Default.ShoppingCart,
            label = "Cart"
        ),
        BottomNavigationItem(
            icon = Icons.Default.Person,
            label = "Profile"
        ),
        BottomNavigationItem(
            icon = Icons.Default.Settings,
            label = "Admin"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when {
                            selectedProduct != null -> "Product Details"
                            showOrdersScreen -> "My Orders"
                            showAdminLogin -> "Admin Login"
                            else -> "Crafted"
                        },
                        color = Color(0xFF8B4513)
                    )
                },
                navigationIcon = {
                    if (selectedProduct != null || showOrdersScreen || showAdminLogin) {
                        IconButton(onClick = {
                            selectedProduct = null
                            showOrdersScreen = false
                            showAdminLogin = false
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF8B4513)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            if (selectedProduct == null && !showOrdersScreen && !showAdminLogin) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = Color(0xFF8B4513)
                ) {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedIndex == index,
                            onClick = { 
                                if (index == 4) { // Admin tab
                                    if (adminViewModel.isUserAdmin()) {
                                        selectedIndex = index
                                    } else {
                                        showAdminLogin = true
                                    }
                                } else {
                                    selectedIndex = index
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                selectedProduct != null -> {
                    ProductDetailScreen(
                        product = selectedProduct!!,
                        onBackClick = { selectedProduct = null },
                        cartViewModel = cartViewModel
                    )
                }
                showOrdersScreen -> {
                    OrdersScreen()
                }
                showAdminLogin -> {
                    AdminLoginScreen(
                        onBackClick = { showAdminLogin = false },
                        onLoginSuccess = { 
                            showAdminLogin = false
                            selectedIndex = 4 // Switch to admin tab
                        }
                    )
                }
                else -> {
                    when (selectedIndex) {
                        0 -> HomeScreen(
                            onProductClick = { product -> selectedProduct = product }
                        )
                        1 -> SearchScreen()
                        2 -> CartScreen()
                        3 -> ProfileScreen(
                            onNavigateToOrders = { showOrdersScreen = true }
                        )
                        4 -> AdminScreen()
                    }
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)


