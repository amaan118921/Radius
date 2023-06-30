package com.example.radius.data.network


import com.example.radius.data.models.FacilitiesModel
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("ad-assignment/db")
    suspend fun getFacilities(): Response<FacilitiesModel>
}