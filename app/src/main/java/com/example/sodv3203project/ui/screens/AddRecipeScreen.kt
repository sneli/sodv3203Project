package com.example.sodv3203project.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.ui.theme.kanit
import com.example.sodv3203project.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddRecipeScreen(viewModel: RecipeViewModel, navController: NavController){
    var recipeTitle by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var cookTime by remember { mutableStateOf("") }
    var servingSize by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    var selectedDifficulty by remember { mutableStateOf("Easy") }

    val mealTypeOptions = listOf("Breakfast", "Smoothie", "Baking", "Lunch", "Dinner")
    val selectedTags = remember { mutableStateListOf<String>() }

    var imageUri by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val savedPath = copyImageToInternalStorage(context, it)
            imageUri = savedPath // Save the file path in internal storage
            Log.d("AddRecipeScreen", "Image copied to: $imageUri")
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item{
            /* Add image and recipe name*/
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri?.let { File(it) }),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 500.dp, height = 300.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                    contentDescription = null,
                    modifier = Modifier
                        .size(500.dp, 300.dp)
                        .padding(16.dp)
                )
            }
            TextButton(
                onClick = { imagePickerLauncher.launch("image/*") }
            ) {
                Text(
                    text = "Upload an image"
                )
            }
            OutlinedTextField(
                value = recipeTitle,
                onValueChange = { recipeTitle = it },
                placeholder = {
                    Text(
                        text = "Recipe Title",
                        fontFamily = kanit,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 20.dp, bottom = 24.dp)
            )

            /* add prep time, cook time, serving size*/
            Row(
                modifier = Modifier.fillParentMaxWidth()
            ){
                Column(){
                    Text(
                        text = "Prep Time:",
                        fontFamily = kanit,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    OutlinedTextField(
                        value = prepTime,
                        onValueChange = { prepTime = it },
                        singleLine = true,
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                    )
                }
                Column(){
                    Text(
                        text = "Cook Time:",
                        fontFamily = kanit,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    OutlinedTextField(
                        value = cookTime,
                        onValueChange = { cookTime = it },
                        singleLine = true,
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                    )
                }
                Column(){
                    Text(
                        text = "Serving Size:",
                        fontFamily = kanit,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    OutlinedTextField(
                        value = servingSize,
                        onValueChange = { servingSize = it },
                        singleLine = true,
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                    )
                }
            }


            /* modify difficulty */
            Column(
                modifier = Modifier.fillParentMaxWidth()
            ){
                Text(
                    text = "Difficulty:",
                    fontFamily = kanit,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                )
                Column {
                    DifficultySelector(selectedDifficulty) { selectedDifficulty = it }
                }
            }


            /* description, ingredients, and instructions textfield */
            Column(
                modifier = Modifier.fillParentMaxWidth()
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    singleLine = false,
                    placeholder = {
                        Text(
                            text = "Description",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Start),
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier
                        .height(300.dp)
                        .fillParentMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                )
                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    singleLine = false,
                    placeholder = {
                        Text(
                            text = "Ingredients",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Start),
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier
                        .height(300.dp)
                        .fillParentMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                )
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    singleLine = false,
                    placeholder = {
                        Text(
                            text = "Instructions",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Start),
                    maxLines = Int.MAX_VALUE,
                    modifier = Modifier
                        .height(300.dp)
                        .fillParentMaxWidth()
//                        .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                        .padding(16.dp, 24.dp)
                )
            }



            /*  meal type tags  */
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Meal Type Tags:",
                style = MaterialTheme.typography.titleMedium,
                fontFamily = kanit,
                fontSize = 16.sp
            )

            mealTypeOptions.forEach { type ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedTags.contains(type),
                        onCheckedChange = { checked ->
                            if (checked) selectedTags.add(type)
                            else selectedTags.remove(type)
                        }
                    )
                    Text(
                        text = type,
                        fontFamily = kanit,
                        fontSize = 14.sp
                    )
                }
            }


            /* final buttons */
            Row(
                modifier = Modifier.fillParentMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                OutlinedButton(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .height(80.dp)
                        .width(200.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10))
                ){
                    Text(
                        text = "Cancel",
                        fontFamily = kanit,
                        fontSize = 16.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        val newRecipe  = Recipe(
                            title = recipeTitle,
                            imageUri = imageUri,
                            prepTime = prepTime,
                            cookTime = cookTime,
                            servingSize = servingSize,
                            description = description,
                            ingredients = ingredients,
                            instructions = instructions,
                            difficulty = selectedDifficulty,
                            mealTypes = selectedTags
                        )
                        viewModel.addRecipe(newRecipe) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Recipe added successfully!")
                            }
                            navController.navigate("home")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .height(80.dp)
                        .width(200.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10))
                ){
                    Text(
                        text = "Publish",
                        fontFamily = kanit,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultySelector(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit
) {
    val difficulties = listOf("Easy", "Medium", "Hard")

    Column {
        difficulties.forEach { difficulty ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDifficultySelected(difficulty) }
            ) {
                RadioButton(
                    selected = difficulty == selectedDifficulty,
                    onClick = { onDifficultySelected(difficulty) }
                )
                Text(
                    text = difficulty,
                    fontSize = 16.sp,
                    fontFamily = kanit,
                    modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "recipe_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        file.absolutePath
    } catch (e: Exception) {
        Log.e("copyImageToInternalStorage", "Error copying image: ${e.message}", e)
        null
    }
}