package com.techncraft.lib.auth.biometric

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt

@RequiresApi(api = Build.VERSION_CODES.R)
class BiometricCallbackV30(private val biometricCallback: BiometricCallback) :
  BiometricPrompt.AuthenticationCallback() {
  override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
    super.onAuthenticationSucceeded(result)
    biometricCallback.onAuthenticationSuccessful()
  }

  override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
    super.onAuthenticationError(errorCode, errString)
    biometricCallback.onAuthenticationError(errorCode, errString)
  }

  override fun onAuthenticationFailed() {
    super.onAuthenticationFailed()
    biometricCallback.onAuthenticationFailed()
  }
}

