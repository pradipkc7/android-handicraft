package com.example.blogapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.blogapp.view.pages.*
import com.example.blogapp.viewModel.AdminViewModel
import com.example.blogapp.viewModel.CartViewModel
import com.example.blogapp.viewModel.ProductViewModel
import com.example.blogapp.viewModel.UserViewModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.repository.UserRepositoryImpl
import com.example.blogapp.model.ProductModel
import com.example.blogapp.view.theme.CraftedTheme

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
    var selectedProduct by remember { mutableStateOf<ProductModel?>(null) }
    var showOrdersScreen by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val activity = context as Activity
    
    val productViewModel = remember { ProductViewModel(ProductRepositoryImpl()) }
    val userViewModel = remember { UserViewModel(UserRepositoryImpl()) }
    val cartViewModel = remember { CartViewModel() }
    
    val bottomNavItems = listOf(
        BottomNavigationItem(
            icon = Icons.Default.Home,
            label = "Home"
        ),
        BottomNavigationItem(
            icon = Icons.Default.Search,
            label = "Search"
        ),
        BottomNavigationItem(
            icon = Icons.Default.ShoppingCart,
            label = "Cart"
        ),
        BottomNavigationItem(
            icon = Icons.Default.Person,
            label = "Profile"
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
                            else -> "Crafted"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                },
                navigationIcon = {
                    if (selectedProduct != null || showOrdersScreen) {
                        IconButton(onClick = {
                            selectedProduct = null
                            showOrdersScreen = false
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF8B4513)
                            )
                        }
                    }
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
            if (selectedProduct == null && !showOrdersScreen) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = Color(0xFF8B4513)
                ) {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { 
                                if (index == 2) { // Cart tab
                                    Box {
                                        Icon(item.icon, contentDescription = item.label)
                                        val cartCount = cartViewModel.getCartItemCount()
                                        if (cartCount > 0) {
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .size(16.dp)
                                                    .background(Color.Red, CircleShape)
                                            ) {
                                                Text(
                                                    text = if (cartCount > 9) "9+" else cartCount.toString(),
                                                    color = Color.White,
                                                    fontSize = 8.sp,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    Icon(item.icon, contentDescription = item.label)
                                }
                            },
                            label = { Text(item.label) },
                            selected = selectedIndex == index,
                            onClick = { 
                                selectedIndex = index
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
                else -> {
                    when (selectedIndex) {
                        0 -> HomeScreen(
                            onProductClick = { product -> selectedProduct = product }
                        )
                        1 -> SearchScreen()
                        2 -> CartScreen(cartViewModel = cartViewModel)
                        3 -> ProfileScreen(
                            onNavigateToOrders = { showOrdersScreen = true }
                        )
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


