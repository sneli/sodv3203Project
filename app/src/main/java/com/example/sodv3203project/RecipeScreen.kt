package com.example.sodv3203project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.room.Room
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.recipe.RecipeRepository
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.navigation.RecipeNavGraph
import com.example.sodv3203project.ui.subscreens.CurrentSession.user
import com.example.sodv3203project.ui.theme.winkle
import com.example.sodv3203project.viewmodel.RecipeViewModel
import com.example.sodv3203project.viewmodel.RecipeViewModelFactory

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "home")
    object Search : BottomNavItem("Search", Icons.Default.Search, "search")
    object Add : BottomNavItem("Add", Icons.Default.Add, "add")
    object Profile : BottomNavItem("Profile", Icons.Default.Person, "profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colorScheme.primary),
        title = {
            Row(){
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.background
                )
                Text(
                    text = stringResource(R.string.app_name),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 50.sp,
                    fontFamily = winkle,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    )
}

@Composable
fun RecipeScreen(repository: RecipeRepository, user: User) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val userDao = db.userDao()

    val factory = remember { RecipeViewModelFactory(db, repository) }
    val recipeViewModel: RecipeViewModel = viewModel(factory = factory)

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { RecipeTopBar() },
        bottomBar = { BottomNavBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        RecipeNavGraph(
            navController = navController,
            userDao = userDao,
            user = user,
            recipeViewModel = recipeViewModel,
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Add,
        BottomNavItem.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationRoute ?: "home") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
