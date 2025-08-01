package com.example.blogapp.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blogapp.R
import com.example.blogapp.repository.UserRepositoryImpl
import com.example.blogapp.ui.theme.BlogAppTheme
import com.example.blogapp.viewModel.UserViewModel
import androidx.compose.foundation.BorderStroke

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Login(innerPadding)
            }
        }
    }
}

@Composable
fun Login(innerPadding: PaddingValues) {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var context = LocalContext.current
    val activity = context as Activity
//    val sharedPreferences=context.getSharedPreferences("User", Context.MODE_PRIVATE)
//    val editor= sharedPreferences.edit()
//    val localEmail : String?=sharedPreferences.getString("email","")
//    val localPassword : String?=sharedPreferences.getString("password","")
//    username=localEmail.toString()
//    password=localPassword.toString()
    Column(modifier = Modifier
        .padding(innerPadding)
        .fillMaxSize()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(20.dp, 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            value = username,
            onValueChange = {username = it},
            label = {Text("Username")},
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            value = password,
            onValueChange = { password = it },
            label = {Text("Password")},
            visualTransformation =
                if (!passwordVisibility) PasswordVisualTransformation()
                else VisualTransformation.None,
            trailingIcon = {
                Icon(
                    painter =
                        if (!passwordVisibility) painterResource(R.drawable.baseline_visibility_24)
                        else painterResource(R.drawable.baseline_visibility_off_24),
                    modifier = Modifier.clickable{
                        passwordVisibility = !passwordVisibility
                    },
                    contentDescription = null,

                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = {
                        rememberMe = !rememberMe
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Green,
                        checkmarkColor = Color.White
                    )
                )
                Text(text = "remember me")
            }
            Row {
                Text(text = "Forget Password?",
                    modifier = Modifier.clickable{}
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(onClick = {

            userViewModel.login(username,password) { success, message ->
                if(success){
                if (rememberMe) {
//                editor.putString("email",username)
//                editor.putString("password",password)
//                editor.apply()
                }
                val intent = Intent(context, NavigationActivity::class.java)
                intent.putExtra("username", username)
                intent.putExtra("password", password)
                context.startActivity(intent)
                activity.finish()
                    Toast.makeText(context,message, Toast.LENGTH_LONG).show()

                }
                else{
                    Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                }
            }
        },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(10.dp, 0.dp)) {
            Text(text = "Login")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Admin Access Button
        OutlinedButton(
            onClick = {
                val intent = Intent(context, AdminNavigationActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF8B4513)
            ),
            border = BorderStroke(1.dp, Color(0xFF8B4513))
        ) {
            Icon(
                Icons.Default.Lock,
                contentDescription = "Admin Access",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Admin Access")
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "New User? Register Now",
            color = Color.Blue,
            modifier = Modifier.clickable{
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            })
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BlogAppTheme {
        Login(innerPadding = PaddingValues(3.dp))
    }
}