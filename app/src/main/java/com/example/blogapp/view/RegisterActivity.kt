package com.example.blogapp.view

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.blogapp.model.UserModel
import com.example.blogapp.repository.UserRepositoryImpl
import com.example.blogapp.ui.theme.BlogAppTheme
import com.example.blogapp.viewModel.UserViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar


class RegisterActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Back") },
                        navigationIcon = {
                            IconButton(onClick = {
                                val context = this@RegisterActivity
                                val intent = android.content.Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                finish()
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Register(innerPadding)
            }
        }
    }
}

@Composable
fun Register(innerPadding: PaddingValues) {
    val repo=remember { UserRepositoryImpl() }
    val userViewModel=remember { UserViewModel(repo) }

    var firstName by remember { mutableStateOf("") };
    var lastName by remember { mutableStateOf("") };
    var email by remember { mutableStateOf("") };
    var rememberMe by remember { mutableStateOf(false) };
    var password by remember { mutableStateOf("")  }

    //for dropdown menu
    var countryExpand by remember { mutableStateOf(false) }
    var selectedTextOptinText by remember { mutableStateOf("Select country") }
    val countryOptions = listOf("country1","country2","country3");
    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    // for date picker
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDate by remember { mutableStateOf("") }

    //Initializing DatePicker
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate ="$selectedDay/${selectedMonth +1}/$selectedYear"
        },
        year,
        month,
        day
    )

    //for gender radio
    val radioOptions = listOf("Male", "Female", "Others")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Register",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.weight(0.5f),
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier.weight(0.5f),
                label = { Text("Last Name") }
            )
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.width(400.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("password") },
                modifier = Modifier.width(400.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .width(400.dp)){
                OutlinedTextField(
                    modifier = Modifier
                        .clickable { countryExpand = true }
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .fillMaxWidth(),
                    value = selectedTextOptinText,
                    onValueChange = {},
                    placeholder = {Text("Select Country")},
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.Transparent,
                        disabledIndicatorColor = Color.Black,
                        disabledTextColor = Color.Black,

                        ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }

                )
                DropdownMenu(
                    expanded = countryExpand,
                    onDismissRequest = {countryExpand = false},
                    modifier = Modifier.width(with(LocalDensity.current) {textFieldSize.width.toDp()})
                ) {
                    countryOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {Text(option)},
                            onClick = {
                                selectedTextOptinText = option
                                countryExpand =false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .width(400.dp)
                .clickable
                    (interactionSource = remember { MutableInteractionSource() },
                    indication = null) { datePickerDialog.show() }) {
                OutlinedTextField(
                    label = {Text("DOB")},
                    value = selectedDate,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.Transparent,
                        disabledIndicatorColor = Color.Black,
                        disabledTextColor = Color.Black,

                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Row (modifier = Modifier.fillMaxWidth().width(400.dp).selectableGroup(),
        ) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
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
            Text(text = "I have read, and accepted to the terms and conditions")
        }
        Spacer(modifier = Modifier.height(20.dp))
        var isFormValid = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && selectedTextOptinText != "Select country" && selectedDate.isNotBlank()
        val activity = context as? android.app.Activity
        ElevatedButton(
            onClick = {
                if (!isFormValid) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                    return@ElevatedButton
                }
                userViewModel.register(email, password) { success, message, userId ->
                    if (success) {
                        val model = UserModel(
                            userId, email, firstName, lastName, "7324577", selectedTextOptinText, password
                        )
                        userViewModel.addUserToDatabase(userId, model) { success, message ->
                            if (success) {
                                Toast.makeText(context, "Register completed", Toast.LENGTH_LONG).show()
                                // Navigate to LoginActivity
                                val intent = android.content.Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                                activity?.finish()
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(10.dp, 0.dp),
            enabled = isFormValid
        ) {
            Text(text = "Register")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun registerPreview1 () {
    BlogAppTheme {
        Register(innerPadding = PaddingValues(3.dp))
    }
}