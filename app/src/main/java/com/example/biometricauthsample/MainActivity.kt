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
import com.example.biometricauthsample.ui.theme.BiometricAuthSampleTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                            runOnUiThread {
                                                Toast.makeText(
                                                    context,
                                                    "Authenticated successfully",
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