package com.party.wear.rps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.repository.RpsRepository
import com.party.shared.rps.state.RpsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RpsViewModel @Inject constructor(private val rpsRepository: RpsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RpsUiState())
    val uiState: StateFlow<RpsUiState> = _uiState

    fun onChoice(choice: RpsChoice) {
        _uiState.update { it.copy(myChoice = choice, isLoading = true) }
        viewModelScope.launch {
            val result = rpsRepository.sendChoice(choice)
            _uiState.update { it.copy(result = result, isLoading = false) }
        }
    }
}