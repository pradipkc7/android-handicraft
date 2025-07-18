package com.example.blogapp.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import coil.compose.AsyncImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blogapp.model.ProductModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.view.ui.theme.BlogAppTheme
import com.example.blogapp.viewModel.ProductViewModel
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.res.painterResource
import com.example.blogapp.R
import android.net.Uri
import android.util.Log
import com.example.blogapp.utils.ImageUtils
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter

//class AddProductActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            Scaffold { innerPadding->
//                AddProductActivity(innerPadding)
//            }
//
//
//        }
//    }
//}
//
//@Composable
//fun AddProductActivity(innerPadding: PaddingValues) {
//
//    val repo= remember { ProductRepositoryImpl() }
//    val viewModel=remember { ProductViewModel(repo) }
//
//    val context= LocalContext.current
//    val activity=context as? Activity
//
//    var pName by remember { mutableStateOf("") }
//    var pDesc by remember { mutableStateOf("") }
//    var pPrice by remember { mutableStateOf("") }
//    var imageUri by remember { mutableStateOf<android.net.Uri?>(null) }
//    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
//        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
//    ) { uri ->
//        imageUri = uri
//    }
//    LazyColumn (modifier= Modifier
//        .fillMaxSize()
//        .padding()) {
//        item{
//            Row(modifier = Modifier.fillMaxWidth()) {
//                IconButton(onClick = {
//                    val intent = android.content.Intent(context, DashboardActivity::class.java)
//                    context.startActivity(intent)
//                    activity?.finish()
//                }) {
//                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Dashboard")
//                }
//                Spacer(modifier = Modifier.weight(1f))
//                Button(onClick = {
//                    val model= ProductModel("",pName, pPrice.toDouble(),pDesc, image = imageUri?.toString())
//                    viewModel.addProduct(model) { success,message->
//                        if (success) {
//                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//                            activity?.finish()
//                        } else {
//                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }){
//                    Text("Add Product")
//                }
//            }
//            // Image picker section
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clickable { launcher.launch("image/*") }
//                    .padding(10.dp)
//            ) {
//                if (imageUri != null) {
//                    AsyncImage(
//                        model = imageUri,
//                        contentDescription = "Selected Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
//                    )
//                } else {
//                    Image(
//                        painter = androidx.compose.ui.res.painterResource(com.example.blogapp.R.drawable.logo),
//                        contentDescription = "Placeholder Image",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
//                    )
//                }
//            }
//            OutlinedTextField(
//                value = pName,
//                onValueChange = {
//                    pName=it
//                },
//                placeholder = { Text("ProductName")}
//            )
//            OutlinedTextField(
//                value = pPrice,
//                onValueChange = {
//                    pPrice=it
//                },
//                placeholder = { Text("ProductPrice")}
//            )
//            OutlinedTextField(
//                value = pDesc,
//                onValueChange = {
//                    pDesc=it
//                },
//                placeholder = { Text("ProductDesc")}
//            )
//        }
//
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview4() {
//    BlogAppTheme {
//        AddProductActivity(PaddingValues(3.dp))
//    }
//}
//


class AddProductActivity : ComponentActivity() {
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

            AddProductBody(
                selectedImageUri = selectedImageUri,
                onPickImage = { imageUtils.launchImagePicker() }
            )
        }
    }
}

@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var pName by remember { mutableStateOf("") }
    var pPrice by remember { mutableStateOf("") }
    var pDesc by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }
    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }
    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { onPickImage() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF2563EB)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2563EB))
                ) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add Image", tint = Color(0xFF2563EB))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add image from gallery")
                }
            }
            if (imageUri != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                value = pName, onValueChange = {
                    pName = it
                }, placeholder = {
                    Text("Product name")
                }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = pDesc, onValueChange = {
                    pDesc = it
                }, placeholder = {
                    Text("Product Description")
                }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = pPrice, onValueChange = {
                    pPrice = it
                }, placeholder = {
                    Text("Product Price")
                }, modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {

                if (selectedImageUri != null) {
                    viewModel.uploadImage(context, selectedImageUri) { imageUrl ->
                        if (imageUrl != null) {
                            val model = ProductModel(
                                "",
                                pName,
                                pPrice.toDouble(),
                                pDesc,
                                imageUrl
                            )
                            viewModel.addProduct(model) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                if (success) activity?.finish()
                            }
                        } else {
                            Log.e("Upload Error", "Failed to upload image to Cloudinary")
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please select an image first",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }) {
                Text("Add product")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductPre() {

    AddProductBody(  selectedImageUri = null, // or pass a mock Uri if needed
        onPickImage = {})
}