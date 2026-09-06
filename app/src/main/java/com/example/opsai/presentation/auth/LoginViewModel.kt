package com.example.opsai.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opsai.domain.repository.AuthRepository
import com.example.opsai.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)
            when (val result = repository.login(email, password)) {
                is Resource.Success -> {
                    _state.value = LoginState(isLoggedIn = true)
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = result.message)
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }
    }
}
