package com.example.sodv3203project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.sodv3203project.ui.AddRecipeScreen
import com.example.sodv3203project.ui.HomeScreen
import com.example.sodv3203project.ui.SearchScreen
import com.example.sodv3203project.ui.ProfileScreen
import com.example.sodv3203project.ui.theme.winkle

sealed class Screen(val route: String, val icon: ImageVector) {
    object Home : Screen("home", Icons.Default.Home)
    object Search : Screen("search", Icons.Default.Search)
    object Add : Screen("Add", Icons.Default.Add)
    object Profile : Screen("profile", Icons.Default.Person)

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
fun RecipeScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { RecipeTopBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = Screen.Add.route) {
                composable(Screen.Home.route)
                {
                    HomeScreen()
                }
                composable(Screen.Search.route)
                {
                    SearchScreen()
                }
                composable(Screen.Add.route)
                {
                    AddRecipeScreen()
                }
                composable(Screen.Profile.route)
                {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Search, Screen.Add, Screen.Profile)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    BottomNavigation(
        backgroundColor = Color.DarkGray,
        modifier = Modifier
            .height(80.dp)
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(
                icon =
                {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route,
                        tint = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.size(40.dp)
                            .align(Alignment.CenterVertically)
                    )
                },
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}
