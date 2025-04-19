package com.example.sodv3203project.ui.subscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import com.example.sodv3203project.viewmodel.RecipeViewModel

@Composable
fun RecipeInstructionsPage(recipeId: Long, viewModel: RecipeViewModel, navController: NavController) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(recipeId) {
        viewModel.getRecipeById(recipeId).collect { fetchedRecipe ->
            recipe = fetchedRecipe
        }
    }

    recipe?.let {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .verticalScroll(rememberScrollState())
        ){
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
            Column(modifier = Modifier.padding(bottom = 16.dp)
                .fillMaxWidth()
            ){
                Text(
                    text = "Ingredients:",
                    fontFamily = winkle,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(bottom = 0.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = it.ingredients,
                    modifier = Modifier.padding(bottom = 4.dp),
                    fontFamily = kanit
                )
            }

            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
            )

            Column(modifier = Modifier.padding(bottom = 16.dp)
                .fillMaxWidth()
            ) {
                Text(
                    text = "Instructions:",
                    fontFamily = winkle,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(bottom = 0.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = it.instructions,
                    fontFamily = kanit
                )
            }
            Button(
                onClick = { navController.navigate("home")},
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Finish recipe",
                    fontFamily = kanit
                )
            }
        }
    }
}