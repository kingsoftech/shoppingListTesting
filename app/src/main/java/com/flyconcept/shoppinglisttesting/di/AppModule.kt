package com.flyconcept.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flyconcept.shoppinglisttesting.data.local.ShoppingItem
import com.flyconcept.shoppinglisttesting.data.local.ShoppingItemDatabase
import com.flyconcept.shoppinglisttesting.data.remote.PixabayAPI
import com.flyconcept.shoppinglisttesting.utils.Constants.BASE_URL
import com.flyconcept.shoppinglisttesting.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}


