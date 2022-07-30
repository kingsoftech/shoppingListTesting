package com.flyconcept.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.flyconcept.shoppinglisttesting.Resource
import com.flyconcept.shoppinglisttesting.data.local.ShoppingDao
import com.flyconcept.shoppinglisttesting.data.local.ShoppingItem
import com.flyconcept.shoppinglisttesting.data.remote.PixabayAPI
import com.flyconcept.shoppinglisttesting.data.remote.response.ImageResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private  val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) :ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
       return shoppingDao.observeAllShoppingItem()
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return  shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try{
        val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?:Resource.error("an unknown error occured", null)
            }
            else{
                Resource.error("an unknown error occured", null)
            }
        }
        catch (e:Exception){
            Resource.error("could'nt reach the server. check your internet connect", null)
        }
    }
}