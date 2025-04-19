package com.example.sodv3203project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sodv3203project.data.recipe.Converters
import com.example.sodv3203project.data.recipe.Recipe
import com.example.sodv3203project.data.recipe.RecipeDao
import com.example.sodv3203project.data.user.User
import com.example.sodv3203project.data.user.UserDao

@Database(entities = [User::class, Recipe::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}