package com.party.wear.rps

import androidx.lifecycle.ViewModel
import com.party.shared.rps.model.RpsChoice
import com.party.shared.rps.model.RpsResult
import com.party.shared.rps.state.RpsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class RpsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(RpsUiState())
    val uiState: StateFlow<RpsUiState> = _uiState

    fun setResult(result: RpsResult) {
        _uiState.update { it.copy(result = result, isLoading = false) }
    }

    fun onChoice(choice: RpsChoice) {
        _uiState.update { it.copy(myChoice = choice, isLoading = true) }
    }
}