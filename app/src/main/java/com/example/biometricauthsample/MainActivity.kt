package com.example.biometricauthsample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.biometricauthsample.ui.theme.BiometricAuthSampleTheme


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.edit().apply {
            putString("user.name", "Joseph Artega")
            putString("user.pin", "1450")
        }.apply()

        setContent {
            val context = LocalContext.current
            BiometricAuthSampleTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("Biometric Authentication")
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextButton(
                                onClick = {
                                    val result = BiometricAuth.status(context)
                                    Toast.makeText(
                                        context,
                                        if (result) "Available" else "Not Available",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    "Availability Status"
                                )
                            }
                            TextButton(
                                onClick = {
                                    BiometricAuth.authenticate(
                                        this@MainActivity,
                                        title = "Biometric Authentication",
                                        subtitle = "Authenticate to proceed",
                                        description = "Authentication is must",
                                        negativeText = "Cancel",
                                        onSuccess = {
                                            var userName =
                                                sharedPreferences.getString("user.name", "")
                                            var userPin =
                                                sharedPreferences.getString("user.pin", "")

                                            runOnUiThread {
                                                Toast.makeText(
                                                    context,
                                                    "Authenticated successfully",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()

                                                Toast.makeText(
                                                    context,
                                                    "$userName : $userPin",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        },
                                        onError = { errorString ->
                                            runOnUiThread {
                                                Toast.makeText(
                                                    context,
                                                    "Authentication error: $errorString",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        },
                                        onFailed = {
                                            runOnUiThread {
                                                Toast.makeText(
                                                    context,
                                                    "Authentication failed",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    "Authenticate"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}