package com.example.blogapp.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import com.example.blogapp.model.UserModel
import com.example.blogapp.viewModel.UserViewModel

@Composable
fun AdminUsersScreen(userViewModel: UserViewModel) {
    val allUsers = userViewModel.allUsers.observeAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf<UserModel?>(null) }
    var showUserDetailsDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        userViewModel.getAllUsers()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "User Management",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search users...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UserStatChip(
                label = "Total Users",
                value = "${allUsers.value?.size ?: 0}",
                color = Color(0xFF8B4513)
            )
            
            UserStatChip(
                label = "Active",
                value = "${allUsers.value?.size ?: 0}",
                color = Color(0xFF4CAF50)
            )
            
            UserStatChip(
                label = "New Today",
                value = "0",
                color = Color(0xFF2196F3)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Users List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filteredUsers = allUsers.value?.filterNotNull()?.filter { user ->
                user.firstName.contains(searchQuery, ignoreCase = true) ||
                user.lastName.contains(searchQuery, ignoreCase = true) ||
                user.email.contains(searchQuery, ignoreCase = true)
            } ?: emptyList()
            
            items(filteredUsers) { user ->
                AdminUserCard(
                    user = user,
                    onViewDetails = {
                        selectedUser = user
                        showUserDetailsDialog = true
                    },
                    onSendMessage = {
                        // Show a toast message for now
                        // TODO: Implement email functionality
                    }
                )
            }
        }
    }
    
    // User Details Dialog
    if (showUserDetailsDialog && selectedUser != null) {
        AlertDialog(
            onDismissRequest = { 
                showUserDetailsDialog = false
                selectedUser = null
            },
            title = { Text("User Details") },
            text = {
                Column {
                    Text("Name: ${selectedUser!!.firstName} ${selectedUser!!.lastName}")
                    Text("Email: ${selectedUser!!.email}")
                    if (selectedUser!!.contact.isNotEmpty()) {
                        Text("Contact: ${selectedUser!!.contact}")
                    }
                    Text("User ID: ${selectedUser!!.userId}")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showUserDetailsDialog = false
                        selectedUser = null
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
fun AdminUserCard(
    user: UserModel,
    onViewDetails: () -> Unit,
    onSendMessage: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
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
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                
                Text(
                    text = user.email,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                if (user.contact.isNotEmpty()) {
                    Text(
                        text = "Contact: ${user.contact}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                
                Text(
                    text = "ID: ${user.userId}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            
            // Action Buttons
            Column {
                IconButton(
                    onClick = onViewDetails,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = "View Details",
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                IconButton(
                    onClick = onSendMessage,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Send Message",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun UserStatChip(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}