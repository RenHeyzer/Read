package com.example.read.feature_auth.data.remote.sources

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await() ?: null

    override suspend fun signInWithEmailAndPassword(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password).await() ?: null

    override val isEmailVerified: Boolean
        get() = if (firebaseAuth.currentUser != null) {
            Log.i("isEmailVerified", firebaseAuth.currentUser!!.toString())
            firebaseAuth.currentUser!!.isEmailVerified
        }
        else {
            Log.i("isEmailVerified", "false")
            false
        }
}