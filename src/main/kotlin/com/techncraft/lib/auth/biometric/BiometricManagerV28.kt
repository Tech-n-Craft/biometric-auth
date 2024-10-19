package com.techncraft.lib.auth.biometric

import android.annotation.TargetApi
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.core.content.ContextCompat

@TargetApi(Build.VERSION_CODES.P)
open class BiometricManagerV28 : BiometricManagerV23() {
  protected var mCancellationSignalV28 = CancellationSignal()

  fun displayBiometricPromptV28(biometricCallback: BiometricCallback) {
    BiometricPrompt.Builder(context)
      .setTitle(title)
      .setSubtitle(subtitle)
      .setDescription(description)
      .setNegativeButton(negativeButtonText, context!!.mainExecutor,
        { _, _ -> biometricCallback.onAuthenticationCancelled() })
      .build()
      .authenticate(
        mCancellationSignalV28, ContextCompat.getMainExecutor(context),
        BiometricCallbackV28(biometricCallback)
      )
  }
}