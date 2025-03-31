package com.example.e_commerceapp.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    fun registerUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = hashMapOf("email" to email, "uid" to auth.currentUser?.uid)
                firestore.collection("users").document(auth.currentUser?.uid.toString()).set(user)
                onComplete(true)
            } else onComplete(false)
        }
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onComplete(it.isSuccessful)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun logoutUser() {
        auth.signOut()
    }

    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }
}
