package com.example.sodv3203project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.ui.subscreens.CurrentSession.user
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.viewmodel.RecipeViewModel
import java.io.File

@Composable
fun ProfileScreen(navController: NavController, user: User, viewModel: RecipeViewModel) {
    val recipes = viewModel.recipes

    LaunchedEffect(viewModel) {
        viewModel.loadRecipes()
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        item{ UserInformation(navController, user) }
        item{ ProfileDivider() }

        items(recipes) { recipe ->
            ProfileRecipeCard(recipe = recipe, navController = navController)
        }
    }
}


@Composable
fun UserInformation(navController: NavController, user: User){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ){
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(start = 40.dp)
        ){
            Text(
                text = user.name,
                fontSize = 30.sp,
                fontFamily = kanit,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = user.username,
                fontSize = 20.sp,
                fontFamily = kanit,
                modifier = Modifier.padding(start = 8.dp)
            )
            OutlinedButton(
                onClick = { navController.navigate("editprofile") },
                modifier = Modifier.padding(top = 12.dp)
            ){
                Text(
                    text = "Edit Profile",
                    fontSize = 20.sp,
                    fontFamily = kanit
                )
            }
        }
    }
}

@Composable
fun ProfileDivider(){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(1f)
            .size(40.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
    ){
        Text(
            text = "Posts",
            fontFamily = kanit,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ProfileRecipeCard(recipe: Recipe, navController: NavController){
    val imageUri = recipe.imageUri

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .size(120.dp)
                    .fillMaxHeight(),
                placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
                error = painterResource(id = android.R.drawable.ic_menu_report_image),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp, 6.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = recipe.title,
                    fontFamily = kanit
                )
                Text(
                    text = "Serves: ${recipe.servingSize}",
                    fontFamily = kanit
                )
                Button(
                    onClick = { navController.navigate("recipe/${recipe.id}") },
                    modifier = Modifier.padding(top = 4.dp)
                        .align(alignment = Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "See Full Recipe",
                        fontSize = 14.sp,
                        fontFamily = kanit,
                    )
                }
            }
        }
    }
}
