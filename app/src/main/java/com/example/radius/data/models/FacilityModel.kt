package com.example.radius.data.models

data class FacilityModel(
    var facility_id: String? = null,
    var name: String? = null,
    var options: List<OptionModel>? = null,
    var selectedOption: String? = null,
    var selectedDraw: Int? = null
)
