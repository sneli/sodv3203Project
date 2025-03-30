package com.example.sodv3203project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // Adds space between sections
    ) {
        item { NewestRecipesSection() }
        item { TopRatedRecipesSection() }
        item { QuickRecipesSection() }
    }
}

@Composable
fun NewestRecipesSection() {
    Column {
        Text(
            text = "Newest Posts",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) { // Example: Show 5 recipe cards
                RecipeCard()
            }
        }
    }
}

@Composable
fun TopRatedRecipesSection() {
    Column {
        Text(
            text = "Top Rated",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) { RecipeCard() }
        }
    }
}

@Composable
fun QuickRecipesSection() {
    Column {
        Text(
            text = "Quick Recipes",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) { RecipeCard() }
        }
    }
}

@Composable
fun RecipeCard() {
    Card(
        modifier = Modifier
            .size(width = 150.dp, height = 220.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(130.dp, 100.dp)
                    .padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id =  android.R.drawable.ic_menu_gallery), // Placeholder
                    contentDescription = null
                )
            }
            Text(text = "Title", fontSize = 16.sp)
            Text(text = "@Username", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Time", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Serving size", fontSize = 14.sp, color = Color.Gray)
            Button(
                onClick = { /* need to implement */ },
                modifier = Modifier.padding(top = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primarySurface
                )
            ) {
                Text(
                    text = "See Full Recipe",
                    fontSize = 12.sp)
            }
        }
    }
}