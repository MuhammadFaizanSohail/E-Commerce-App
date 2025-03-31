package com.example.e_commerceapp.di.databasemodule

import android.content.Context
import com.example.e_commerceapp.data.local.dao.CartDao
import com.example.e_commerceapp.data.local.dao.OrderDao
import com.example.e_commerceapp.data.local.dao.ReviewDao
import com.example.e_commerceapp.data.local.database.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ProductsDatabase {
        return ProductsDatabase.getInstance(appContext)
    }

    @Provides
    fun provideDao(database: ProductsDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideReviewsDao(database: ProductsDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    fun provideOrderDao(database: ProductsDatabase): OrderDao {
        return database.orderDao()
    }
}
