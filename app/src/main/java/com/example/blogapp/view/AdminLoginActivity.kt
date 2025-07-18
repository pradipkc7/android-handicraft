package com.example.blogapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.blogapp.view.pages.AdminLoginScreen
import com.example.blogapp.view.theme.CraftedTheme

class AdminLoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CraftedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AdminLoginScreen(
                        onBackClick = {
                            finish()
                        },
                        onLoginSuccess = {
                            // Navigate to main app with admin privileges
                            val intent = Intent(this, NavigationActivity::class.java)
                            intent.putExtra("isAdmin", true)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
} 