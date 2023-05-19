package com.example.api_github.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.usecase.GetUsersUseCase
import com.example.api_github.domain.usecase.SearchUsersUseCase
import com.example.api_github.presenter.model.UserUiModel
import com.example.api_github.presenter.model.toUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val searchUsersUseCase: SearchUsersUseCase
) : ViewModel() {

    private val _pageState = MutableLiveData<PageState>(PageState.Idle)
    val pageState: LiveData<PageState> get() = _pageState

    fun getUsers() {
        viewModelScope.launch {
            _pageState.value = PageState.Loading
            _pageState.value = when (val result = getUsersUseCase.invoke()) {
                is BaseResult.Failure -> PageState.Error
                is BaseResult.Success -> PageState.Loaded(result.value.map { it.toUserUiModel() })
            }
        }
    }

    fun searchUsers(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                _pageState.value = PageState.Loading
                _pageState.value = when (val result = searchUsersUseCase.invoke(query)) {
                    is BaseResult.Failure -> PageState.Error
                    is BaseResult.Success -> PageState.Loaded(result.value.map { it.toUserUiModel() })
                }
            }
        } else {
            getUsers()
        }
    }

    sealed class PageState {
        object Idle : PageState()
        object Loading : PageState()
        object Error : PageState()
        data class Loaded(val users: List<UserUiModel>) : PageState()
    }

}
