package com.example.blogapp.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import android.content.Intent
import com.example.blogapp.view.LoginActivity

@Composable
fun AdminSettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var autoBackup by remember { mutableStateOf(true) }
    var showAnalytics by remember { mutableStateOf(true) }
    
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Admin Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8B4513)
            )
        }
        
        item {
            Text(
                text = "Configure your admin panel preferences",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        
        item {
            SettingsSection(title = "Appearance") {
                SettingsSwitch(
                    title = "Dark Mode",
                    subtitle = "Use dark theme for admin panel",
                    icon = Icons.Default.Settings,
                    checked = darkMode,
                    onCheckedChange = { darkMode = it }
                )
            }
        }
        
        item {
            SettingsSection(title = "Notifications") {
                SettingsSwitch(
                    title = "Push Notifications",
                    subtitle = "Receive notifications for new orders and users",
                    icon = Icons.Default.Notifications,
                    checked = notifications,
                    onCheckedChange = { notifications = it }
                )
            }
        }
        
        item {
            SettingsSection(title = "Data Management") {
                SettingsSwitch(
                    title = "Auto Backup",
                    subtitle = "Automatically backup database daily",
                    icon = Icons.Default.Settings,
                    checked = autoBackup,
                    onCheckedChange = { autoBackup = it }
                )
                
                SettingsItem(
                    title = "Export Data",
                    subtitle = "Export all data to CSV",
                    icon = Icons.Default.Info,
                    onClick = { /* TODO: Export data */ }
                )
                
                SettingsItem(
                    title = "Clear Cache",
                    subtitle = "Clear app cache and temporary files",
                    icon = Icons.Default.Clear,
                    onClick = { /* TODO: Clear cache */ }
                )
            }
        }
        
        item {
            SettingsSection(title = "Analytics") {
                SettingsSwitch(
                    title = "Show Analytics",
                    subtitle = "Display analytics and insights",
                    icon = Icons.Default.Info,
                    checked = showAnalytics,
                    onCheckedChange = { showAnalytics = it }
                )
                
                SettingsItem(
                    title = "Generate Report",
                    subtitle = "Create detailed admin report",
                    icon = Icons.Default.Info,
                    onClick = { /* TODO: Generate report */ }
                )
            }
        }
        
        item {
            SettingsSection(title = "Security") {
                SettingsItem(
                    title = "Change Password",
                    subtitle = "Update admin password",
                    icon = Icons.Default.Lock,
                    onClick = { /* TODO: Change password */ }
                )
                
                SettingsItem(
                    title = "Two-Factor Auth",
                    subtitle = "Enable 2FA for admin account",
                    icon = Icons.Default.Lock,
                    onClick = { /* TODO: Setup 2FA */ }
                )
            }
        }
        
        item {
            SettingsSection(title = "About") {
                SettingsItem(
                    title = "App Version",
                    subtitle = "Version 1.0.0",
                    icon = Icons.Default.Info,
                    onClick = { /* Show version info */ }
                )
                
                SettingsItem(
                    title = "Terms of Service",
                    subtitle = "Read terms and conditions",
                    icon = Icons.Default.Info,
                    onClick = { /* Show terms */ }
                )
                
                SettingsItem(
                    title = "Privacy Policy",
                    subtitle = "Read privacy policy",
                    icon = Icons.Default.Info,
                    onClick = { /* Show privacy policy */ }
                )
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = "Logout",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout from Admin Panel")
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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
                text = subtitle,
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

@Composable
fun SettingsSwitch(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
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
                text = subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF8B4513),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            )
        )
    }
} 