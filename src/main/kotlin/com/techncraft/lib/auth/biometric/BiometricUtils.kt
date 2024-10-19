package com.techncraft.lib.auth.biometric

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object BiometricUtils {

  @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
  val isBiometricPromptEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

  /*
   * Condition I: Check if the android version in device is greater than
   * Marshmallow, since biometric authentication is only supported
   * from Android 6.0.
   */
  @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
  val isSdkVersionSupported: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

  /*
   * Condition II: Check if the device supports biometric authentication
   */
  fun isBiometricAvailable(context: Context): Boolean {
    val biometricManager = BiometricManager.from(context)
    return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
      BiometricManager.BIOMETRIC_SUCCESS -> true
      else -> false
    }
  }

  /*
   * Condition III: Check if the permission has been added to
   * the app. This permission will be granted as soon as the user
   * installs the app on their device.
   */
  @RequiresApi(Build.VERSION_CODES.P)
  fun isPermissionGranted(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_BIOMETRIC) == PackageManager.PERMISSION_GRANTED
  }

  /*
   * Function to authenticate using BiometricPrompt
   */
  fun authenticateUser(
    activity: FragmentActivity,
    callback: BiometricPrompt.AuthenticationCallback
  ) {
    // Create executor to run the biometric prompt
    val executor: Executor = Executors.newSingleThreadExecutor()

    // Set up the biometric prompt
    val biometricPrompt = BiometricPrompt(activity, executor, callback)

    // Set up the prompt information
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
      .setTitle("Biometric Authentication")
      .setSubtitle("Authenticate using your biometric credential")
      .setNegativeButtonText("Cancel")
      .build()

    // Start authentication
    biometricPrompt.authenticate(promptInfo)
  }
}
