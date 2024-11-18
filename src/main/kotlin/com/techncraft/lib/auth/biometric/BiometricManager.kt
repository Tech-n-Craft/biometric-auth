package com.techncraft.lib.auth.biometric

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.techncraft.lib.auth.biometric.BiometricUtils.isBiometricAvailable
import com.techncraft.lib.auth.biometric.BiometricUtils.isPermissionGranted


open class BiometricManager protected constructor(biometricBuilder: BiometricBuilder) : BaseBiometricManagerV30() {
  fun authenticate(biometricCallback: BiometricCallback) {
    if (title.isEmpty()) {
      biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null")
      return
    }
    if (subtitle.isEmpty()) {
      biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null")
      return
    }
    if (description.isEmpty()) {
      biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null")
      return
    }
    if (negativeButtonText.isEmpty()) {
      biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null")
      return
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && !isPermissionGranted(context)) {
      biometricCallback.onBiometricAuthenticationPermissionNotGranted()
      return
    }
    if (!isBiometricAvailable(context)) {
      biometricCallback.onBiometricAuthenticationNotSupported()
      return
    }
    displayBiometricDialog(biometricCallback)
  }

  fun cancelAuthentication() {
    when (Build.VERSION.SDK_INT) {
      Build.VERSION_CODES.R -> if (!mCancellationSignalV30.isCanceled) mCancellationSignalV30.cancel()
      Build.VERSION_CODES.P -> if (!mCancellationSignalV28.isCanceled) mCancellationSignalV28.cancel()
    }
  }

  private fun displayBiometricDialog(biometricCallback: BiometricCallback) {
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> displayBiometricPromptV30(biometricCallback)

      Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> displayBiometricPromptV28(biometricCallback)
    }
  }

  class BiometricBuilder(val context: AppCompatActivity) {
    private var title: String? = null
    private var subtitle: String? = null
    private var description: String? = null
    private var negativeButtonText: String? = null

    fun getTitle() = title ?: ""

    fun setTitle(title: String): BiometricBuilder {
      this.title = title
      return this
    }

    fun getSubtitle() = subtitle ?: ""

    fun setSubtitle(subtitle: String): BiometricBuilder {
      this.subtitle = subtitle
      return this
    }

    fun getDescription() = description ?: ""

    fun setDescription(description: String): BiometricBuilder {
      this.description = description
      return this
    }

    fun getNegativeButtonText() = negativeButtonText ?: ""

    fun setNegativeButtonText(negativeButtonText: String): BiometricBuilder {
      this.negativeButtonText = negativeButtonText
      return this
    }

    fun build(): BiometricManager {
      return BiometricManager(this)
    }
  }

  init {
    context = biometricBuilder.context
    title = biometricBuilder.getTitle()
    subtitle = biometricBuilder.getSubtitle()
    description = biometricBuilder.getDescription()
    negativeButtonText = biometricBuilder.getNegativeButtonText()
  }
}