package com.example.radius.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.radius.data.models.FacilitiesModel
import com.example.radius.helpers.ResultState
import com.example.radius.usecase.GetFacilityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RadiusViewModel @Inject constructor(private val getFacilityUseCase: GetFacilityUseCase) : ViewModel() {

    private var _facility = MutableLiveData<ResultState<FacilitiesModel>>()


    fun getFacilityLiveData(): LiveData<ResultState<FacilitiesModel>> {
        return _facility
    }

    fun getFacilities() {
        viewModelScope.launch {
            _facility.postValue(
                getFacilityUseCase.getFacility()
            )
        }
    }


}