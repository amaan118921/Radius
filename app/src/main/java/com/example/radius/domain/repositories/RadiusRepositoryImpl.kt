package com.example.radius.domain.repositories

import com.example.radius.data.models.FacilitiesModel
import com.example.radius.data.network.Api
import com.example.radius.data.repositories.RadiusRepository
import retrofit2.Response
import javax.inject.Inject

class RadiusRepositoryImpl @Inject constructor(private val api: Api): RadiusRepository {
    override suspend fun getFacility(): Response<FacilitiesModel> {
        return api.getFacilities()
    }
}