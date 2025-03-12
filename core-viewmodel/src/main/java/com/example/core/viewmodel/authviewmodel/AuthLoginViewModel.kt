package com.example.core.viewmodel.authviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.model.users.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthSate>()
    val authSate: LiveData<AuthSate> = _authState
    init {
        checkAuthSate()
    }

    fun checkAuthSate(){
        if (auth.currentUser == null){
            _authState.value = AuthSate.Unauthenticated
        }else{
            _authState.value = AuthSate.Authenticated
        }
    }
    fun loginAuthState(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthSate.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthSate.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
                if (task.isSuccessful){
                    _authState.value = AuthSate.Authenticated
                }else{
                    _authState.value = AuthSate.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }
    fun signupAuthState(email: String, password: String, name: String, phoneNumber: String){
//        if (email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthSate.Error("Email or password can't be empty")
//            return
//        }
//        _authState.value = AuthSate.Loading
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener{
//                    task ->
//                if (task.isSuccessful){
//                    _authState.value = AuthSate.Authenticated
//                }else{
//                    _authState.value = AuthSate.Error(task.exception?.message?:"Something went wrong")
//                }
//            }
        if (email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            _authState.value = AuthSate.Error("Email, password, or phone number can't be empty")
            return
        }

        _authState.value = AuthSate.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val userModel = UserModel(userId, email, name, phoneNumber)

                        FirebaseFirestore.getInstance().collection("users")
                            .document(userId)
                            .set(userModel)
                            .addOnSuccessListener {
                                _authState.value = AuthSate.Authenticated
                            }
                            .addOnFailureListener { e ->
                                _authState.value = AuthSate.Error(e.message ?: "Failed to save user data")
                            }
                    } else {
                        _authState.value = AuthSate.Error("User ID is null")
                    }
                } else {
                    _authState.value = AuthSate.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }
    fun signOut(){
        auth.signOut()
        _authState.value = AuthSate.Unauthenticated
    }

}
sealed class AuthSate{
    object Authenticated: AuthSate()
    object Unauthenticated: AuthSate()
    object Loading: AuthSate()
    data class Error(var message: String): AuthSate()
}