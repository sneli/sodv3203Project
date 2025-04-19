package com.example.sodv3203project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.recipe.RecipeRepository

class RecipeViewModelFactory(
    private val db: AppDatabase,
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(db, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}