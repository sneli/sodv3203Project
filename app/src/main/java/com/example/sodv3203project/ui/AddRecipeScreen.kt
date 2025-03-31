package com.example.sodv3203project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sodv3203project.ui.theme.kanit
import kotlin.math.sin

@Composable
fun AddRecipeScreen(){
    var recipeTitle by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var cookTime by remember { mutableStateOf("") }
    var servingSize by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item{
            /* Add image and recipe name*/
            Image(
                painter = painterResource(id =  android.R.drawable.ic_menu_gallery), // Placeholder
                contentDescription = null,
                modifier = Modifier.size(500.dp, 300.dp)
                    .padding(16.dp, bottom = 0.dp)
            )
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
                Row(){
                    OutlinedButton(
                        onClick = {/**/},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                            .clip(RoundedCornerShape(10))
                    ){
                        Text(
                            text = "Easy",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    }
                    OutlinedButton(
                        onClick = {/**/},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                            .clip(RoundedCornerShape(10))
                    ){
                        Text(
                            text = "Medium",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    }
                    OutlinedButton(
                        onClick = {/**/},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.background
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .width(130.dp)
                            .padding(16.dp, bottom = 0.dp)
                            .clip(RoundedCornerShape(10))
                    ){
                        Text(
                            text = "Hard",
                            fontFamily = kanit,
                            fontSize = 16.sp
                        )
                    }
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



            /* final buttons */
            Row(
                modifier = Modifier.fillParentMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                OutlinedButton(
                    onClick = {/**/},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background
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
                    onClick = {/**/},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background
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

