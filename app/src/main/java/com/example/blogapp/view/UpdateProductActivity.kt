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

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.model.ProductModel
import com.example.blogapp.repository.ProductRepositoryImpl
import com.example.blogapp.utils.ImageUtils
import com.example.blogapp.view.ui.theme.BlogAppTheme
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

@Composable
fun UpdateProductBody( selectedImageUri: Uri?,
                       onPickImage: () -> Unit) {
    var pName by remember { mutableStateOf("") }
    var pPrice by remember { mutableStateOf("") }
    var pDesc by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as? Activity

    val productId : String? = activity?.intent?.getStringExtra("productId")

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.products.observeAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.getProductById(productId.toString())
    }

    pName = products.value?.productName ?: ""
    pDesc = products.value?.productDesc ?: ""
    pPrice = products.value?.productPrice.toString()


    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Top Row with Back Icon
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, DashboardActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Dashboard")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        onPickImage()
                    }
                    .padding(10.dp)
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
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
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

            Row (modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    viewModel.uploadImage(context, selectedImageUri!!) { imageUrl ->
                        if(imageUrl != null){
                            var maps = mutableMapOf<String, Any?>()

                            maps["productId"] = productId
                            maps["name"] = pName
                            maps["desc"] = pDesc
                            maps["price"] = pPrice.toInt()
                            maps["imageUrl"] = imageUrl

                            viewModel.updateProduct(productId.toString(), maps) { success, message ->
                                if (success) {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    activity?.finish()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                                }
                            }
                        }
                    }
                }) {
                    Text("Update product")
                }
            }

        }
    }

}


@Preview
@Composable
fun previewUpdate(){
    UpdateProductBody(
        selectedImageUri = null, // or pass a mock Uri if needed
        onPickImage = {})
}