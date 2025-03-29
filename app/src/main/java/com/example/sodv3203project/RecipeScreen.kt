package com.example.sodv3203project

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.sodv3203project.ui.HomeScreen
import com.example.sodv3203project.ui.NotificationScreen
import com.example.sodv3203project.ui.SearchScreen
import com.example.sodv3203project.ui.ProfileScreen

sealed class Screen(val route: String, val icon: ImageVector) {
    object Home : Screen("home", Icons.Default.Home)
    object Search : Screen("search", Icons.Default.Search)
    object Notifications : Screen("notifications", Icons.Default.Notifications)
    object Profile : Screen("profile", Icons.Default.Person)

}


@Composable
fun RecipeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route)
                {
                    HomeScreen()
                }
                composable(Screen.Search.route)
                {
                    SearchScreen()
                }
                composable(Screen.Notifications.route)
                {
                    NotificationScreen()
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
    val items = listOf(Screen.Home, Screen.Search, Screen.Notifications, Screen.Profile)
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
