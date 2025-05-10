package com.contrast.Contrast.presentation.features.login

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
class BiometricAuthenticator(
    private val activity: FragmentActivity
) {
    fun canAuthenticate(): Boolean {
        val biometricManager = BiometricManager.from(activity)
        val result = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_WEAK  or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        Log.d("BiometricTest", "canAuthenticate() result = $result")
        return result == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticate(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                onError(errString.toString())

            }

            override fun onAuthenticationFailed() {
                onError("Xác thực thất bại")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Đăng nhập bằng sinh trắc học")
            .setSubtitle("Xác thực bằng vân tay hoặc mã PIN")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_WEAK  or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        prompt.authenticate(promptInfo)
    }
}
