package com.example.blogapp.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.R

@Composable
fun ProfileScreen(
    onNavigateToOrders: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Profile Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.craft),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable { 
                        // Handle profile picture click
                        println("Profile picture clicked")
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sarah Johnson",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "sarah.johnson@email.com",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Member since 2023",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Profile Stats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat("Orders", "12") {
                onNavigateToOrders()
            }
            ProfileStat("Wishlist", "8") {
                println("Wishlist clicked")
            }
            ProfileStat("Reviews", "5") {
                println("Reviews clicked")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Menu Items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(profileMenuItems) { item ->
                ProfileMenuItem(item = item) {
                    // Handle menu item click
                    when (item.title) {
                        "My Orders" -> onNavigateToOrders()
                        "Wishlist" -> println("Wishlist clicked")
                        "Settings" -> println("Settings clicked")
                        "Help & Support" -> println("Help & Support clicked")
                        "About" -> println("About clicked")
                        "Logout" -> println("Logout clicked")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStat(label: String, value: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileMenuItem(item: ProfileMenuItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = item.iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Navigate",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

data class ProfileMenuItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color
)

val profileMenuItems = listOf(
    ProfileMenuItem("My Orders", Icons.Default.Star, Color(0xFF8B4513)),
    ProfileMenuItem("Wishlist", Icons.Default.FavoriteBorder, Color(0xFFE91E63)),
    ProfileMenuItem("Addresses", Icons.Default.LocationOn, Color(0xFF2196F3)),
    ProfileMenuItem("Payment Methods", Icons.Default.ThumbUp, Color(0xFF4CAF50)),
    ProfileMenuItem("Settings", Icons.Default.Settings, Color(0xFF9E9E9E)),
    ProfileMenuItem("Help & Support", Icons.Default.Home, Color(0xFF607D8B)),
    ProfileMenuItem("About", Icons.Default.Info, Color(0xFF795548)),
    ProfileMenuItem("Logout", Icons.Default.ExitToApp, Color(0xFFF44336))
)