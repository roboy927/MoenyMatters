package com.kanishthika.moneymatters.display.googleSignIn

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.kanishthika.moneymatters.config.mmComposable.MMColumnScaffoldContentColumn
import com.kanishthika.moneymatters.config.mmComposable.MMTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Launcher for Google Sign-In intent
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        googleSignInViewModel.handleSignInResult(result.data) { account ->
            if (account != null) {
                // Handle successful sign-in
                Toast.makeText(context, "Signed in as ${account.displayName}", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Handle sign-in failure
                Toast.makeText(context, "Sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Trigger upload when the screen starts


    Scaffold(
        topBar = { MMTopAppBar(titleText = "Sign In") }
    ) { paddingValues ->
        MMColumnScaffoldContentColumn(modifier = modifier, scaffoldPaddingValues = paddingValues) {
            Column {
                Button(onClick = {
                    val signInIntent = googleSignInViewModel.googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }) {
                    Text("Sign In with Google")
                }
                Button(onClick = {
                    googleSignInViewModel.signOut(
                        onSuccess = {
                            Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT)
                                .show()
                        },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "Sign-out failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }) {
                    Text("Sign Out")
                }

            }
        }
    }
}

