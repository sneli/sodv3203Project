package com.example.sodv3203project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.viewmodel.RecipeViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: RecipeViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedServingSize by remember { mutableStateOf("All") }
    var selectedDifficulty by remember { mutableStateOf("All") }
    var selectedMealType by remember { mutableStateOf("All") }

    val recipes = viewModel.recipes.filter { recipe ->
        val matchesQuery = recipe.title.contains(searchQuery, ignoreCase = true)
        val matchesServing = selectedServingSize == "All" || recipe.servingSize == selectedServingSize
        val matchesDifficulty = selectedDifficulty == "All" || recipe.difficulty == selectedDifficulty
        val matchesMealType = selectedMealType == "All" || recipe.mealTypes.contains(selectedMealType)
        matchesQuery && matchesServing && matchesDifficulty && matchesMealType
    }

    fun resetFilters() {
        selectedServingSize = "All"
        selectedDifficulty = "All"
        selectedMealType = "All"
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Search bar
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = {
                    Text(
                        text = "Search recipes",
                        fontFamily = kanit
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
        }

        // Filter dropdowns
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                DropdownFilter(
                    label = "Difficulty",
                    options = listOf("All", "Easy", "Medium", "Hard"),
                    selectedOption = selectedDifficulty,
                    onOptionSelected = { selectedDifficulty = it }
                )
                DropdownFilter(
                    label = "Meal Type",
                    options = listOf("All", "Breakfast", "Smoothie", "Baking", "Lunch", "Dinner"),
                    selectedOption = selectedMealType,
                    onOptionSelected = { selectedMealType = it}
                )
            }
        }

        OutlinedButton(
            onClick = { resetFilters() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Reset Filters",
                fontFamily = kanit
            )
        }

        // Recipe results
        LazyColumn {
            items(recipes) { recipe ->
                SearchRecipeCard(recipe, navController)
            }
        }
    }
}

@Composable
fun DropdownFilter(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.width(180.dp)
        ) {
            Text(
                text = "$label: $selectedOption",
                fontFamily = kanit,
                fontSize = 14.sp
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            fontFamily = kanit
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SearchRecipeCard(recipe: Recipe, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable{navController.navigate(("recipe/${recipe.id}"))},
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = kanit
            )
            Text(
                text = "Prep Time: ${recipe.prepTime} | Cook Time: ${recipe.cookTime}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kanit
            )
            Text(
                text = "Serving Size: ${recipe.servingSize}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kanit
            )
            Text(
                text = "Difficulty: ${recipe.difficulty}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kanit
            )
            if (!recipe.imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = recipe.imageUri,
                    contentDescription = "${recipe.title} Image",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}