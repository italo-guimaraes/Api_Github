package com.example.api_github.presenter.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_github.domain.BaseResult
import com.example.api_github.domain.usecase.GetUserDetailUseCase
import com.example.api_github.presenter.model.UserDetailUiModel
import com.example.api_github.presenter.model.toUserDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _pageState = MutableLiveData<PageState>(PageState.Idle)
    val pageState: LiveData<PageState> get() = _pageState

    fun getUserDetail(login: String) {
        viewModelScope.launch {
            _pageState.value = PageState.Loading
            _pageState.value = when (val result = getUserDetailUseCase.invoke(login)) {
                is BaseResult.Failure -> PageState.Error
                is BaseResult.Success -> PageState.Loaded(result.value.toUserDetailUiModel())
            }
        }
    }

    sealed class PageState {
        object Idle : PageState()
        object Loading : PageState()
        object Error : PageState()
        data class Loaded(val user: UserDetailUiModel) : PageState()
    }

}