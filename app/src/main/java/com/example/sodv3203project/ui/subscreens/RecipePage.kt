package com.example.sodv3203project.ui.subscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.ui.theme.winkle
import com.example.sodv3203project.viewmodel.RecipeViewModel
import java.io.File

@Composable
fun RecipePage(recipeId: Long, viewModel: RecipeViewModel, navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val recipe by viewModel.getRecipeById(recipeId).collectAsState(initial = null)

    recipe?.let {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
            ) {
                if (it.imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(File(it.imageUri)),
                        contentDescription = "Recipe Image",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // optional padding inside image area
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit Recipe") },
                                onClick = {
                                    showMenu = false
                                    navController.navigate("editrecipe/${recipe!!.id}")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete Recipe") },
                                onClick = {
                                    showMenu = false
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ){
                Text(
                    text = it.title,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(top = 16.dp)
                        .fillMaxWidth(),
                    maxLines = 3,
                    fontFamily = winkle
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "Prep Time: ${it.prepTime}",
                        fontSize = 14.sp,
                        fontFamily = kanit
                    )
                    Text(
                        text = "Cook Time: ${it.cookTime}",
                        fontSize = 14.sp,
                        fontFamily = kanit
                    )
                    Text(
                        text = "Serving Size: ${it.servingSize}",
                        fontSize = 14.sp,
                        fontFamily = kanit
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
                Text(
                    text = it.description,
                    fontFamily = kanit
                )

                Button(
                    onClick = { navController.navigate("instructions/${it.id}") },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = "Start Cooking!",
                        fontFamily = kanit
                    )
                }
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Confirm Delete") },
                    text = { Text("Are you sure you want to delete this recipe?") },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.deleteRecipe(it.id) {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}
