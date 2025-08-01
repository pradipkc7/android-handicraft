package com.example.blogapp.view
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.model.ProductModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.utils.ImageUtils
import com.example.blogapp.viewModel.ProductViewModel

class UpdateProductActivity : ComponentActivity() {
    lateinit var imageUtils: ImageUtils
    var selectedImageUri by mutableStateOf<Uri?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        imageUtils = ImageUtils(this, this)
        imageUtils.registerLaunchers { uri ->
            selectedImageUri = uri
        }
        setContent {
            UpdateProductBody(
                selectedImageUri = selectedImageUri,
                onPickImage = { imageUtils.launchImagePicker() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductBody(selectedImageUri: Uri?, onPickImage: () -> Unit) {
    var pName by remember { mutableStateOf("") }
    var pPrice by remember { mutableStateOf("") }
    var pDesc by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val activity = context as? Activity

    val productId: String? = activity?.intent?.getStringExtra("productId")

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.products.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        if (!productId.isNullOrEmpty()) {
            viewModel.getProductById(productId)
        }
    }

    // Update form fields when product data is loaded
    LaunchedEffect(products.value) {
        products.value?.let { product ->
            pName = product.productName
            pDesc = product.productDesc
            pPrice = product.productPrice.toString()
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, DashboardActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Dashboard")
                }
                
                Text(
                    text = "Edit Product",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )
                
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onPickImage() },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        selectedImageUri != null -> {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        !products.value?.productImage.isNullOrEmpty() -> {
                            AsyncImage(
                                model = products.value!!.productImage,
                                contentDescription = "Existing Image from DB",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Add Image",
                                    modifier = Modifier.size(48.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Click to change product image",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product Name
            OutlinedTextField(
                value = pName,
                onValueChange = { pName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Product Name")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product Description
            OutlinedTextField(
                value = pDesc,
                onValueChange = { pDesc = it },
                label = { Text("Product Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = "Description")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product Price
            OutlinedTextField(
                value = pPrice,
                onValueChange = { pPrice = it },
                label = { Text("Product Price") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Price")
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Update Button
            Button(
                onClick = {
                    if (pName.isNotEmpty() && pPrice.isNotEmpty() && pDesc.isNotEmpty()) {
                        isLoading = true
                        val price = pPrice.toDoubleOrNull() ?: 0.0
                        
                        // If new image is selected, upload it first
                        if (selectedImageUri != null) {
                            viewModel.uploadImage(context, selectedImageUri!!) { imageUrl ->
                                if (imageUrl != null) {
                                    val maps = mutableMapOf<String, Any?>()
                                    maps["productId"] = productId
                                    maps["name"] = pName
                                    maps["desc"] = pDesc
                                    maps["price"] = price
                                    maps["imageUrl"] = imageUrl

                                    viewModel.updateProduct(productId.toString(), maps) { success, message ->
                                        isLoading = false
                                        if (success) {
                                            showSuccessDialog = true
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    isLoading = false
                                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            // No new image, update without changing image
                            val maps = mutableMapOf<String, Any?>()
                            maps["productId"] = productId
                            maps["name"] = pName
                            maps["desc"] = pDesc
                            maps["price"] = price
                            // Keep existing image URL
                            maps["imageUrl"] = products.value?.productImage ?: ""

                            viewModel.updateProduct(productId.toString(), maps) { success, message ->
                                isLoading = false
                                if (success) {
                                    showSuccessDialog = true
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && pName.isNotEmpty() && pPrice.isNotEmpty() && pDesc.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B4513)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Update Product",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Update Product")
                }
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success!") },
            text = { Text("Product has been updated successfully.") },
            confirmButton = {
                TextButton(
                    onClick = { 
                        showSuccessDialog = false
                        val intent = Intent(context, DashboardActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}


