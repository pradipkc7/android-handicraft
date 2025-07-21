package com.example.blogapp.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.blogapp.R
import com.example.blogapp.model.ProductModel


@Composable
fun FeaturedProductCard(product: ProductModel) {
    Card (
        modifier = Modifier
            .width(160.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            if (!product.productImage.isNullOrEmpty()) {
                AsyncImage(
                    model = product.productImage,
                    contentDescription = product.productName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.craft)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.craft),
                    contentDescription = product.productName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(product.productName, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("$${product.productPrice}", fontSize = 12.sp, color = Color.Gray)

                if (product.rating > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { i ->
                            Icon(
                                imageVector = if (i < product.rating.toInt()) Icons.Filled.Star else Icons.Default.Star,
                                contentDescription = null,
                                tint = if (i < product.rating.toInt()) Color(0xFFFFD700) else Color.Gray,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("(${product.reviewCount})", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}