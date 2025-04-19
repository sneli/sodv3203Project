package com.example.sodv3203project.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.data.user.UserDao
import com.example.sodv3203project.ui.screens.AddRecipeScreen
import com.example.sodv3203project.ui.screens.HomeScreen
import com.example.sodv3203project.ui.screens.ProfileScreen
import com.example.sodv3203project.ui.screens.SearchScreen
import com.example.sodv3203project.ui.subscreens.CurrentSession
import com.example.sodv3203project.ui.subscreens.EditProfile
import com.example.sodv3203project.ui.subscreens.EditRecipe
import com.example.sodv3203project.ui.subscreens.RecipePage
import com.example.sodv3203project.ui.subscreens.RecipeInstructionsPage
import com.example.sodv3203project.viewmodel.RecipeViewModel

@Composable
fun RecipeNavGraph(
    navController: NavHostController,
    userDao: UserDao,
    user: User,
    recipeViewModel: RecipeViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ){
        composable("home") {
            HomeScreen(recipeViewModel, navController)
        }
        composable("search") {
            SearchScreen(navController, recipeViewModel)
        }
        composable("add") {
            AddRecipeScreen(recipeViewModel, navController)
        }
        composable("profile"){
            val user = CurrentSession.user
            if (user != null) {
                ProfileScreen(navController, user, recipeViewModel)
            }
        }

        composable("recipe/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let {
                RecipePage(recipeId = it, viewModel = recipeViewModel, navController = navController)
            }
        }

        composable("editrecipe/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let {
                EditRecipe(recipeId = it, viewModel = recipeViewModel, navController = navController)
            }
        }

        composable("instructions/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull()
            id?.let {
                RecipeInstructionsPage(recipeId = it, viewModel = recipeViewModel, navController)
            }
        }
        composable("editprofile") {
            EditProfile(user, navController, userDao)
        }
    }
}