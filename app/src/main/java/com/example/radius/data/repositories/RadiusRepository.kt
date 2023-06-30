package com.example.radius.data.repositories

import com.example.radius.data.models.FacilitiesModel
import retrofit2.Response


interface RadiusRepository {
    suspend fun getFacility(): Response<FacilitiesModel>
}