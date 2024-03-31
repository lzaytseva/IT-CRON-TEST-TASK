package com.github.lzaytseva.it_cron_test_task.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.lzaytseva.it_cron_test_task.domain.model.UserDetails
import com.github.lzaytseva.it_cron_test_task.domain.usecase.GetUserDetailsUseCase
import com.github.lzaytseva.it_cron_test_task.presentation.state.UserDetailsScreenState
import com.github.lzaytseva.it_cron_test_task.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UserDetailsViewModel(
    private val username: String,
    private val userDetailsUseCase: GetUserDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UserDetailsScreenState>()
    val uiState: LiveData<UserDetailsScreenState>
        get() = _uiState


    private var compositeDisposable = CompositeDisposable()

    init {
        loadDetails()
    }

    fun loadDetails() {
        val disposable = userDetailsUseCase.invoke(username)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _uiState.value = UserDetailsScreenState.Loading
            }
            .subscribe { resource ->
                processResult(resource)
            }
        compositeDisposable.add(disposable)
    }

    private fun processResult(
        resource: Resource<UserDetails>
    ) {
        when (resource) {
            is Resource.Error -> {
                _uiState.value = UserDetailsScreenState.Error(
                    error = resource.errorType!!
                )
            }

            is Resource.Success -> {
                _uiState.value = UserDetailsScreenState.Content(
                    userDetails = resource.data!!
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
