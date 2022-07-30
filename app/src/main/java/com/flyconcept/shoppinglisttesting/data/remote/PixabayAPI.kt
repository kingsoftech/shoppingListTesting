package com.flyconcept.shoppinglisttesting.data.remote

import com.flyconcept.shoppinglisttesting.BuildConfig
import com.flyconcept.shoppinglisttesting.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey:String = BuildConfig.API_KEY
    ):Response<ImageResponse>
}