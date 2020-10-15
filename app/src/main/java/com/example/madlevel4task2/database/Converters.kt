package com.example.madlevel4task2.database

import androidx.room.TypeConverter
import com.example.madlevel4task2.model.Choice

class Converters {
    @TypeConverter
    fun fromChoice(choice: Choice?) = choice?.ordinal

    @TypeConverter
    fun intToChoice(int: Int?) = int?.let { Choice.values()[it] }
}