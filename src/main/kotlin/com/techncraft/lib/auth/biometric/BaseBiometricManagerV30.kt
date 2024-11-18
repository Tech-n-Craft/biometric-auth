package com.techncraft.lib.auth.biometric

import android.annotation.TargetApi
import android.os.Build
import android.os.CancellationSignal
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

@TargetApi(Build.VERSION_CODES.R)
open class BaseBiometricManagerV30 : BaseBiometricManagerV28() {
  protected var mCancellationSignalV30 = CancellationSignal()

  fun displayBiometricPromptV30(biometricCallback: BiometricCallback) {
    val biometricPrompt = BiometricPrompt(
      context,
      ContextCompat.getMainExecutor(context),
      BiometricCallbackV30(biometricCallback)
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
      .setTitle(title)
      .setSubtitle(context.getString(R.string.biometric_subtitle_on_login_30))
      .setDescription(description)
      .setAllowedAuthenticators(DEVICE_CREDENTIAL or BIOMETRIC_STRONG)
      .build()

    biometricPrompt.authenticate(promptInfo)
  }
}