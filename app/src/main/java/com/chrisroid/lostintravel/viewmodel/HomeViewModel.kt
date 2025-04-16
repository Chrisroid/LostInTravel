package com.chrisroid.lostintravel.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisroid.lostintravel.GetRecommendedPlacesQuery
import com.chrisroid.lostintravel.data.api.TravelApiService
import com.chrisroid.lostintravel.data.repository.AuthRepository
import com.chrisroid.lostintravel.domain.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    val destinations: StateFlow<List<Destination>> = _destinations

    var graphQLResponseModel: MutableLiveData<List<GetRecommendedPlacesQuery.RecommendedPlace?>?> = MutableLiveData()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    init {
        loadDestinations()
    }

    fun loadDestinations() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val apiResponse = authRepository.getRecommendedPlaces()
                graphQLResponseModel.postValue(apiResponse)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load destinations"
//                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}