package com.example.sodv3203project.data.recipe

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    suspend fun addRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(id: Long) {
        recipeDao.deleteRecipeById(id)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes()
    }

    fun getRecipeById(id: Long): Flow<Recipe?> = recipeDao.getRecipeById(id)

    suspend fun getRecipesByMealType(mealType: String): Flow<List<Recipe>> {
        return recipeDao.getRecipesByMealType(mealType)
    }


}