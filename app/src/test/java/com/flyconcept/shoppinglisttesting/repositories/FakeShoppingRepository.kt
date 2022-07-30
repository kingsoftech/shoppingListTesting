package com.flyconcept.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flyconcept.shoppinglisttesting.Resource
import com.flyconcept.shoppinglisttesting.data.local.ShoppingItem
import com.flyconcept.shoppinglisttesting.data.remote.response.ImageResponse

class FakeShoppingRepository :ShoppingRepository{

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }
   private fun refreshShoppingItems(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    private fun getTotalPrice():Float{
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
       shoppingItems.add(shoppingItem)
        refreshShoppingItems()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshShoppingItems()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return  if(shouldReturnNetworkError){
            Resource.error("Error", null)
        }else{
            Resource.success(ImageResponse(listOf(), 0,0))
        }
    }
}