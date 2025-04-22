package com.contrast.Contrast.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contrast.Contrast.utils.NetworkChecker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor(
    private val networkChecker: NetworkChecker,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun launchWithNetworkCheck(
        onSuccess: suspend () -> Unit,
        onError: (suspend () -> Unit)? = null
    ) {
        viewModelScope.launch(dispatcher) {
            if (networkChecker.isInternetAvailable()) {
                onSuccess()
            } else {
                Log.e("networkChecker","networkChecker")
                onError?.invoke()
            }
        }
    }
}
