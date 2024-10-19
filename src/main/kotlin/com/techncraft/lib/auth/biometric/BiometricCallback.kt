package com.techncraft.lib.auth.biometric

abstract class BiometricCallback {
  open fun onSdkVersionNotSupported() {}

  open fun onBiometricAuthenticationNotSupported() {}

  open fun onBiometricAuthenticationNotAvailable() {}

  open fun onBiometricAuthenticationPermissionNotGranted() {}

  open fun onBiometricAuthenticationInternalError(error: String?) {}


  open fun onAuthenticationFailed() {}

  abstract fun onAuthenticationCancelled()

  abstract fun onAuthenticationSuccessful()

  open fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {}

  abstract fun onAuthenticationError(errorCode: Int, errString: CharSequence?)
}