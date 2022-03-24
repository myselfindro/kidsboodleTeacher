package com.kidsboodle.teacher.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
   // private const val BASE_URL = "http://148.66.128.232:8002/"
    private const val BASE_URL = "http://3.17.248.213:8002/"


 /*   username:souvik.test@gmail.com
    password:teacher1234*/


    private fun getRetrofit(): Retrofit {
        val httpClient =
            OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}