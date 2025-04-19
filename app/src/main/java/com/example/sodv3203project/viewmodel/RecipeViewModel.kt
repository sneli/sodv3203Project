//package com.example.sodv3203project.viewmodel
//
//import android.app.Application
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.sodv3203project.data.AppDatabase
//import com.example.sodv3203project.data.recipe.Recipe
//import com.example.sodv3203project.data.recipe.RecipeRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class RecipeViewModel(private val db: AppDatabase, private val repository: RecipeRepository) : ViewModel() {
//    private val _recipes = mutableStateListOf<Recipe>()
//    val recipes: List<Recipe> get() = _recipes
//
//    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
//    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes
//
//    init {
//        loadRecipes()
//    }
//
//    fun loadRecipes() {
//        viewModelScope.launch {
//            val loadedRecipes = db.recipeDao().getAllRecipes()
//            _recipes.clear()
//            _recipes.addAll(loadedRecipes)
//        }
//    }
//
//    fun addRecipe(recipe: Recipe, onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            repository.addRecipe(recipe)
//            onSuccess()
//            loadRecipes()
//        }
//    }
//
//    fun deleteRecipe(recipeId: Long, onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            repository.deleteRecipe(recipeId)
//            onSuccess()
//            loadRecipes()
//        }
//    }
//
//    fun updateRecipe(recipe: Recipe, onSuccess: () -> Unit) {
//        viewModelScope.launch {
//            repository.updateRecipe(recipe)
//            onSuccess()
//            loadRecipes()
//        }
//    }
//
//    fun getRecipeById(id: Long): Flow<Recipe?> = repository.getRecipeById(id)
//
//    fun filterRecipesByMealType(mealType: String) {
//        viewModelScope.launch {
//            repository.getRecipesByMealType(mealType = mealType)
//                .collect { result ->
//                    _filteredRecipes.value = result
//                }
//        }
//    }
//}


package com.example.sodv3203project.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203project.data.AppDatabase
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.data.recipe.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val db: AppDatabase, private val repository: RecipeRepository) : ViewModel() {
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> get() = _recipes

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            val loadedRecipes = db.recipeDao().getAllRecipes()
            _recipes.clear()
            _recipes.addAll(loadedRecipes)

            _filteredRecipes.value = loadedRecipes
        }
    }

    fun addRecipe(recipe: Recipe, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.addRecipe(recipe)
            onSuccess()
            loadRecipes()
        }
    }

    fun deleteRecipe(recipeId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deleteRecipe(recipeId)
            onSuccess()
            loadRecipes()
        }
    }

    fun updateRecipe(recipe: Recipe, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
            onSuccess()
            loadRecipes()
        }
    }

    fun getRecipeById(id: Long): Flow<Recipe?> = repository.getRecipeById(id)

    fun filterRecipesByMealType(mealType: String) {
        viewModelScope.launch {
            repository.getRecipesByMealType(mealType = mealType)
                .collect { result ->
                    _filteredRecipes.value = result
                }
        }
    }
}