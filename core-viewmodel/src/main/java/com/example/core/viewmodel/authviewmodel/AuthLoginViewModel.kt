package com.example.core.viewmodel.authviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.model.users.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel: ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthSate>()
    private val _user = MutableLiveData<UserModel?>()
    val authSate: LiveData<AuthSate> = _authState
    val user: LiveData<UserModel?> = _user
    init {
        checkAuthSate()
    }

    fun checkAuthSate(){
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _authState.value = AuthSate.Unauthenticated
            Log.d("AuthViewModel", "User is Unauthenticated")
        } else {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role")
                        _authState.value = if (role == "admin") AuthSate.Admin else AuthSate.User
                        Log.d("AuthViewModel", "User role: $role")
                    } else {
                        _authState.value = AuthSate.Error("User data not found")
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthSate.Error(e.message ?: "Failed to fetch user role")
                }
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
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role")
                                    if (role == "admin") {
                                        _authState.value = AuthSate.Admin
                                    } else {
                                        _authState.value = AuthSate.User
                                    }
                                } else {
                                    _authState.value = AuthSate.Error("User data not found")
                                }
                            }
                            .addOnFailureListener { e ->
                                _authState.value = AuthSate.Error(e.message ?: "Failed to fetch user role")
                            }
                    } else {
                        _authState.value = AuthSate.Error("User ID is null")
                    }
                }else{
                    _authState.value = AuthSate.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }
    fun signupAuthState(email: String, password: String, name: String,
                        phoneNumber: String, localImageUrl: String, role: String){
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
                        val userModel = UserModel(userId, email, name, phoneNumber, localImageUrl, role)

                        FirebaseFirestore.getInstance().collection("users")
                            .document(userId)
                            .set(userModel)
                            .addOnSuccessListener {
                                checkAuthSate() // Cập nhật trạng thái sau khi đăng ký
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
    fun getCurrentUser() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.d("AuthViewModel", "Không có người dùng nào đang đăng nhập")
            _user.value = null
            return
        }
        Log.d("AuthViewModel", "Đang lấy thông tin người dùng với ID: $userId")
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.toObject(UserModel::class.java)
                    _user.value = userData
                } else {
                    _user.value = null
                }
            }
            .addOnFailureListener {exception ->
                _user.value = null
            }
    }
    fun signOut(){
        auth.signOut()
        _authState.value = AuthSate.Unauthenticated
    }

}
sealed class AuthSate{
    object Unauthenticated: AuthSate()
    object Loading: AuthSate()
    object Admin: AuthSate()
    object User: AuthSate()
    data class Error(var message: String): AuthSate()
}