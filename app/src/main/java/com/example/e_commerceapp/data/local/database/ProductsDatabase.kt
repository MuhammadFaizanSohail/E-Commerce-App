package com.example.e_commerceapp.data.local.database

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.entities.CartItem
import com.example.e_commerceapp.data.local.entities.Order
import com.example.e_commerceapp.data.local.entities.Review

@Database(entities = [CartItem::class, Review::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun reviewDao(): ReviewDao
    abstract fun orderDao(): OrderDao

    companion object {
        const val DATABASE_NAME = "products_database"

        @Volatile
        private var INSTANCE: ProductsDatabase? = null

        fun getInstance(context: Context): ProductsDatabase {
            return INSTANCE ?: synchronized(this) {
                val contextA = ContextCompat.createDeviceProtectedStorageContext(context)
                    ?: context.applicationContext
                val instance = Room.databaseBuilder(
                    contextA,
                    ProductsDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}