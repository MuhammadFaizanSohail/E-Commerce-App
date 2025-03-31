package com.example.e_commerceapp.data.local.database

import androidx.room.TypeConverter
import com.example.e_commerceapp.data.local.entities.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCartItemList(items: List<CartItem>): String {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun toCartItemList(json: String): List<CartItem> {
        val type = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(json, type)
    }
}
