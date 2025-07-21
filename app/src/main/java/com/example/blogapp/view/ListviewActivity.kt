package com.example.blogapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ListviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListBody()

        }
    }
}
@Composable
fun ListBody() {
    val images=listOf(
        com.example.blogapp.R.drawable.logo,
        com.example.blogapp.R.drawable.baseline_visibility_24,
        com.example.blogapp.R.drawable.baseline_visibility_off_24,
        com.example.blogapp.R.drawable.logo

    )
    val name=listOf(
        "cute",
        "apk",
        "android",
        "dhurba",

    )


    Scaffold { padding ->
    LazyColumn (modifier = Modifier.fillMaxSize().padding(padding)){
//    items (1000){ index->
//        Text(("$index"))
//    }
        item {
            LazyRow (modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)  ){
                items(images.size) { index ->
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(images[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(shape = CircleShape)
                                .border(width = 2.dp, color = Color.Blue)

                        )
                        Text(name[index])
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                items(images.size) { index ->
                    Image(
                        painter = painterResource(images[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(shape = CircleShape)
                            .border(width = 2.dp, color = Color.Blue)

                    )
                }
            }

            Box(
      modifier = Modifier
                       .height(150.dp)
                        .width(400.dp)
          .padding(10.dp)
                        .background(color = Color.Yellow)
                )
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp)
                    .padding(10.dp)
                    .background(color = Color.Green)
            )
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp)
                    .padding(10.dp)
                    .background(color = Color.Red)
            )
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp)
                    .padding(10.dp)
                    .background(color = Color.Blue)
            )
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp).padding(10.dp)

                    .background(color = Color.Black)
            )
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp)
                    .padding(10.dp)
                    .background(color = Color.Gray)
            )
        }
    }
}
//    val scrollState = rememberScrollState()
//
//    val scrollState2 = rememberScrollState()
//
//
//
//    Scaffold { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .horizontalScroll(scrollState2)
//
//            ) {
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.Red)
//                )
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.Magenta)
//                )
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.Yellow)
//                )
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.Green)
//                )
//
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.DarkGray)
//                )
//                Box(
//                    modifier = Modifier
//                        .height(100.dp)
//                        .width(100.dp)
//                        .background(color = Color.Cyan)
//                )
//
//
//            }
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .background(color = Color.Black)
//            )
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .background(color = Color.Gray)
//            )
//
//
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .background(color = Color.Magenta)
//            )
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .background(color = Color.Green)
//            )
//
//            Box(
//                modifier = Modifier
//                    .height(300.dp)
//                    .fillMaxWidth()
//                    .padding(20.dp)
//                    .background(color = Color.Yellow)
//            )
//        }
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingListView() {
    ListBody()
}
