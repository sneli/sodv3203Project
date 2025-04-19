package com.example.sodv3203project.data.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val imageUri: String? = null,
    val prepTime: String,
    val cookTime: String,
    val servingSize: String,
    val difficulty: String,
    val description: String,
    val ingredients: String,
    val instructions: String,
    val mealTypes: List<String> = listOf()
)
