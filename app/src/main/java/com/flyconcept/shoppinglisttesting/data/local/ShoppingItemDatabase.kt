package com.flyconcept.shoppinglisttesting.data.local

import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase :RoomDatabase(){
    abstract fun shoppingDao(): ShoppingDao
}