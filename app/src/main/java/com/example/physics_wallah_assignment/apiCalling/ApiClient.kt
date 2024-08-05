package com.example.physics_wallah_assignment.apiCalling

import com.example.physics_wallah_assignment.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit : Retrofit? =null

    fun getApi() : Retrofit? {
        if(retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(Utils().BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}