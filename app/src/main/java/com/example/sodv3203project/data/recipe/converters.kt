package com.example.sodv3203project.data.recipe

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromMealTypesList(mealTypes: List<String>): String {
        return mealTypes.joinToString(",")
    }

    @TypeConverter
    fun toMealTypesList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }
}