package com.techncraft.lib.auth.biometric

import androidx.appcompat.app.AppCompatActivity


open class BaseBiometricManager {
  protected lateinit var context: AppCompatActivity
  protected var title: String = ""
  protected var subtitle: String = ""
  protected var description: String = ""
  protected var negativeButtonText: String = ""
}