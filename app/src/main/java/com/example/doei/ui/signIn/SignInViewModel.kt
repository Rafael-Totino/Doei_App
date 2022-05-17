package com.example.doei.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.doei.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {
    private val currentUser = firebaseAuthRepository.getCurrentUser
    fun handleCurrentUser(): LiveData<FirebaseUser?> = currentUser

    private val errorMessage = firebaseAuthRepository.authError
    fun handleAuthError(): LiveData<String?> = errorMessage

    fun registerUser(email: String, password: String) {
        firebaseAuthRepository.register(email, password)
    }
}