package com.example.radius.data.models

data class FacilitiesModel(
    var facilities: List<FacilityModel>? = null, var exclusions: List<List<ExclusionModel>>? = null
)
