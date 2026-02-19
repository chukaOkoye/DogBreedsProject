package com.example.dogbreedsproject.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlin.collections.emptyMap

class RoomConverters {
    private val json = Json
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>{
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMapStrings(value: Map<String, List<String>>): String{
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toMapStrings(value: String): Map<String, List<String>>{
        return json.decodeFromString<Map<String, List<String>>>(value)

    }
}