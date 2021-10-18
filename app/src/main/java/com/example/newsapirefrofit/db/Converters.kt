package com.example.newsapirefrofit.db

import androidx.room.TypeConverter
import com.example.newsapirefrofit.models.Source

class Converters
{
    @TypeConverter
    fun fromSoure(source: Source): String{
        return source.name
    }
    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}