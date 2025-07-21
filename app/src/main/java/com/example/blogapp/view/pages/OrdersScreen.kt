package com.example.blogapp.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun OrdersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "My Orders",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${orders.size} orders",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Orders List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(orders) { order ->
                OrderCard(order = order)
            }
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Order Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Order #${order.orderNumber}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = order.orderDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                StatusChip(status = order.status)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ FIXED: Use Column instead of nested LazyColumn
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                order.items.forEach { item ->
                    OrderItemRow(item = item)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Order Summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total: ${order.total}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "${order.items.size} items",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.height(32.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4513))
                    ) {
                        Text(
                            text = "Track Order",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItemRow(item: OrderItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "Qty: ${item.quantity}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Text(
            text = item.price,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF8B4513)
        )
    }
}

@Composable
fun StatusChip(status: OrderStatus) {
    val (backgroundColor, textColor) = when (status) {
        OrderStatus.DELIVERED -> Color(0xFF22C55E) to Color.White
        OrderStatus.SHIPPED -> Color(0xFF3B82F6) to Color.White
        OrderStatus.PROCESSING -> Color(0xFFF59E0B) to Color.White
        OrderStatus.CANCELLED -> Color(0xFFEF4444) to Color.White
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

data class Order(
    val orderNumber: String,
    val orderDate: String,
    val status: OrderStatus,
    val total: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val name: String,
    val price: String,
    val quantity: Int,
    val imageRes: Int
)

enum class OrderStatus {
    DELIVERED,
    SHIPPED,
    PROCESSING,
    CANCELLED
}

val orders = listOf(
    Order(
        orderNumber = "ORD-2024-001",
        orderDate = "March 15, 2024",
        status = OrderStatus.DELIVERED,
        total = "$127.00",
        items = listOf(
            OrderItem("Elegant Ceramic Vase", "$45.00", 1, R.drawable.craft),
            OrderItem("Handwoven Basket", "$32.00", 2, R.drawable.craft),
            OrderItem("Wooden Cutting Board", "$28.00", 1, R.drawable.craft)
        )
    ),
    Order(
        orderNumber = "ORD-2024-002",
        orderDate = "March 10, 2024",
        status = OrderStatus.SHIPPED,
        total = "$89.00",
        items = listOf(
            OrderItem("Handmade Necklace", "$45.00", 1, R.drawable.logo),
            OrderItem("Ceramic Mug Set", "$24.00", 1, R.drawable.craft),
            OrderItem("Leather Journal", "$20.00", 1, R.drawable.craft)
        )
    ),
    Order(
        orderNumber = "ORD-2024-003",
        orderDate = "March 5, 2024",
        status = OrderStatus.PROCESSING,
        total = "$156.00",
        items = listOf(
            OrderItem("Woven Wall Hanging", "$65.00", 1, R.drawable.craft),
            OrderItem("Glass Vase Collection", "$89.00", 1, R.drawable.craft)
        )
    ),
    Order(
        orderNumber = "ORD-2024-004",
        orderDate = "March 1, 2024",
        status = OrderStatus.CANCELLED,
        total = "$78.00",
        items = listOf(
            OrderItem("Handcrafted Wooden Bowl", "$38.00", 1, R.drawable.craft),
            OrderItem("Linen Table Runner", "$28.00", 1, R.drawable.craft),
            OrderItem("Hand-carved Wooden Spoon", "$12.00", 1, R.drawable.craft)
        )
    )
)
