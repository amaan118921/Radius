package com.example.radius.usecase

import com.example.radius.data.models.FacilitiesModel
import com.example.radius.data.repositories.RadiusRepository
import com.example.radius.helpers.ResultState
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFacilityUseCase @Inject constructor(private val repository: RadiusRepository) {
    suspend fun getFacility(): ResultState<FacilitiesModel> {
        val model: FacilitiesModel?

        try {
            model = repository.getFacility().body()
        } catch (e: Exception) {
            return ResultState.Error(null, e.localizedMessage)
        }
        return ResultState.Success(model)
    }
}