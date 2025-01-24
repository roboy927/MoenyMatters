package com.kanishthika.moneymatters.display.googleSignIn

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    fun handleSignInResult(data: Intent?, onResult: (GoogleSignInAccount?) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            onResult(account)
        } catch (e: ApiException) {
            onResult(null)
        }
    }

    fun signOut(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        googleSignInClient.signOut()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let(onFailure)
                }
            }
    }


}
