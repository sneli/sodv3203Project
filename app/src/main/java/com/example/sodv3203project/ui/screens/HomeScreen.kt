package com.example.sodv3203project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.ui.subscreens.CurrentSession.user
import com.example.sodv3203project.ui.theme.Sodv3203ProjectTheme
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import com.example.sodv3203project.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(viewModel: RecipeViewModel, navController: NavController) {
    val recipes = viewModel.recipes

    LaunchedEffect(Unit) {
        viewModel.loadRecipes()
    }

    val smoothieRecipes = recipes.filter {
        it.mealTypes.any { type -> type.equals("smoothie", ignoreCase = true) }
    }

    val breakfastRecipes = recipes.filter {
        it.mealTypes.any { type -> type.equals("breakfast", ignoreCase = true) }
    }

    LazyColumn(
        modifier = Modifier.padding(top = 24.dp)
    ) {
        item {
            Text(
                text = "Smoothies",
                fontSize = 30.sp,
                fontFamily = winkle,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            SmoothieRecipes(smoothieRecipes, navController)
        }
        item {
            Text(
                text = "Breakfast",
                fontSize = 30.sp,
                fontFamily = winkle,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            BreakfastRecipes(breakfastRecipes, navController)
        }
        item {
            Text(
                text = "All Recipes",
                fontSize = 30.sp,
                fontFamily = winkle,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            AllRecipes(recipes, navController)
        }
    }

}

@Composable
fun SmoothieRecipes(recipes: List<Recipe>, navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, navController = navController)
        }
    }
}

@Composable
fun BreakfastRecipes(recipes: List<Recipe>, navController: NavController) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(bottom = 24.dp)
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, navController = navController)
        }
    }
}

@Composable
fun AllRecipes(recipes: List<Recipe>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier
            .heightIn(max = 500.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, navController = navController)
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, navController: NavController) {
    val imageUri = recipe.imageUri

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .width(180.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(3f / 2f),
                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                error = painterResource(id = android.R.drawable.ic_menu_report_image),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = kanit,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                user?.let {
                    Text(
                        text = "@${it.username}",
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontFamily = kanit,
                    )
                }
                Text(
                    text = "Serves: ${recipe.servingSize}",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontFamily = kanit,
                )
            }
            Button(
                onClick = { navController.navigate("recipe/${recipe.id}") },
                modifier = Modifier.padding(top = 4.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "See Full Recipe",
                    fontSize = 12.sp,
                    fontFamily = kanit,
                )
            }
        }
    }
}