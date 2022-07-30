package com.flyconcept.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.model.FileLoader
import com.flyconcept.shoppinglisttesting.Resource
import com.flyconcept.shoppinglisttesting.data.local.ShoppingItem
import com.flyconcept.shoppinglisttesting.data.remote.response.ImageResponse
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun  deleteShoppingItem(shoppingItem: ShoppingItem)

     fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

     fun observerTotalPrice(): LiveData<Float>

     suspend fun searchForImage(imageQuery:String): Resource<ImageResponse>

}