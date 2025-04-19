package com.example.sodv3203project

import android.app.Application
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.recipe.RecipeRepository

class RecipeApplication : Application() {
    lateinit var repository: RecipeRepository

    override fun onCreate(){
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        repository = RecipeRepository(database.recipeDao())
    }
}