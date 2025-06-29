package com.example.core.viewmodel.authviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.chat.Message
import com.example.core.model.users.UserAuthModel
import com.example.core.viewmodel.apiviewmodel.ApiCallViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Suppress("DEPRECATION")
class AuthViewModel : ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthSate>()
    private val _user = MutableLiveData<UserAuthModel?>()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val authSate: LiveData<AuthSate> = _authState
    val user: LiveData<UserAuthModel?> = _user
    val messages: StateFlow<List<Message>> = _messages
    private val _adminChatRoomsLiveData = MutableLiveData<List<String>>()
    val adminChatRoomsLiveData: LiveData<List<String>> = _adminChatRoomsLiveData
    private var chatRoomId: String? = null
    private val adminId = "Gg7Fu0RguISyjEy7POHO3aSn71F2"
    private var adminChatRooms: List<String> = emptyList()
    val apiCallViewModel: ApiCallViewModel = ApiCallViewModel()

    init {
        checkAuthSate()
    }

    init {
        viewModelScope.launch {
            getOrCreateChatRoom()
            loadMessages()
        }
    }

    suspend fun getOrCreateChatRoom() {
        val userId = auth.currentUser?.uid ?: return
        val userDoc = db.collection("users").document(userId).get().await()
        val userRole = userDoc.getString("role")

        if (userRole == "admin") {
            
            loadAdminChatRooms()
        } else {
            
            
            val chatRoomsRef = db.collection("chat_rooms")
            val query = chatRoomsRef.whereArrayContains("users", userId)
            val querySnapshot = query.get().await()

            chatRoomId = querySnapshot.documents.firstOrNull {
                (it.get("users") as? List<*>)?.contains(adminId) == true && it.getString("adminId") == adminId
            }?.id

            if (chatRoomId == null) {
                
                val newChatRoom = hashMapOf(
                    "users" to listOf(userId, adminId),
                    "adminId" to adminId
                )
                val newChatRoomRef = chatRoomsRef.add(newChatRoom).await()
                chatRoomId = newChatRoomRef.id
            }
        }
    }

    private fun loadAdminChatRooms() {
        viewModelScope.launch {
            db.collection("chat_rooms")
                .whereEqualTo("adminId", adminId)
                .get()
                .addOnSuccessListener { documents ->
                    val chatRooms = documents.map { it.id }
                    adminChatRooms = chatRooms
                    _adminChatRoomsLiveData.value = chatRooms
                    Log.d("ChatViewModel", "loadAdminChatRooms: $chatRooms")
                }
                .addOnFailureListener { exception ->
                    Log.w("ChatViewModel", "Error getting admin chat rooms: ", exception)
                }
        }
    }

    fun loadMessages() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val userDoc = db.collection("users").document(userId).get().await()
            val userRole = userDoc.getString("role")

            if (userRole == "admin") {
                chatRoomId?.let { roomId ->
                    db.collection("chat_rooms")
                        .document(roomId)
                        .collection("messages")
                        .orderBy("timestamp")
                        .addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                
                                return@addSnapshotListener
                            }

                            if (snapshot != null && !snapshot.isEmpty) {
                                val messageList = snapshot.documents.map { doc ->
                                    doc.toObject(Message::class.java) ?: Message()
                                }
                                
                                _messages.value = messageList.reversed()
                            } else {
                                _messages.value = emptyList()
                            }
                        }
                } ?: run {
                    
                    _messages.value = emptyList()
                }
            } else {
                
                chatRoomId?.let { roomId ->
                    db.collection("chat_rooms")
                        .document(roomId)
                        .collection("messages")
                        .orderBy("timestamp")
                        .addSnapshotListener { snapshot, e ->
                            if (e != null) {
                                
                                return@addSnapshotListener
                            }

                            if (snapshot != null && !snapshot.isEmpty) {
                                val messageList = snapshot.documents.map { doc ->
                                    doc.toObject(Message::class.java) ?: Message()
                                }
                                
                                _messages.value = messageList.reversed()
                            } else {
                                _messages.value = emptyList()
                            }
                        }
                }
            }
        }
    }

    fun sendMessage(text: String) {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val userDoc = db.collection("users").document(userId).get().await()
            val userRole = userDoc.getString("role")

            if (userRole == "admin") {
                
                chatRoomId?.let { roomId ->
                    val message = Message(senderId = userId, text = text)
                    db.collection("chat_rooms")
                        .document(roomId)
                        .collection("messages")
                        .add(message)
                        .addOnSuccessListener {
                            
                        }
                        .addOnFailureListener { e ->
                            
                        }
                }
            } else {
                chatRoomId?.let { roomId ->
                    val message = Message(senderId = userId, text = text)
                    db.collection("chat_rooms")
                        .document(roomId)
                        .collection("messages")
                        .add(message)
                        .addOnSuccessListener {
                            
                        }
                        .addOnFailureListener { e ->
                            
                        }
                }
            }
        }
    }
    fun sendMessage1(roomId: String, text: String) {
        val userId = auth.currentUser?.uid
        if (userId == null || text.isBlank()) {
            
            return
        }
        viewModelScope.launch {
            try {
                val message = Message(senderId = userId, text = text)
                db.collection("chat_rooms")
                    .document(roomId)
                    .collection("messages")
                    .add(message)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener { e ->
                        Log.e("AuthViewModel", "Error sending message to room $roomId", e)
                    }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "An unexpected error occurred while sending message", e)
            }
        }
    }
    fun selectChatRoom(roomId: String) {
        chatRoomId = roomId
        loadMessages()
    }
    fun checkAuthSate() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _authState.value = AuthSate.Unauthenticated
        } else {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val role = document.getString("role")
                        _authState.value = if (role == "admin") AuthSate.Admin else AuthSate.User
                    } else {
                        _authState.value = AuthSate.Error("User data not found")
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthSate.Error(e.message ?: "Failed to fetch user role")
                }
        }
    }
    fun loginAuthState(email: String, password: String,
                       onResult: (String) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthSate.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthSate.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role")
                                    if (role == "admin") {
                                        _authState.value = AuthSate.Admin
                                        onResult("Đăng nhập thành công")
                                    } else {
                                        _authState.value = AuthSate.User
                                        onResult("Đăng nhập thành công")
                                    }
                                } else {
                                    _authState.value = AuthSate.Error("User data not found")
                                }
                            }
                            .addOnFailureListener { e ->
                                _authState.value =
                                    AuthSate.Error(e.message ?: "Failed to fetch user role")
                            }
                    } else {
                        _authState.value = AuthSate.Error("User ID is null")
                    }
                } else {
                    _authState.value =
                        AuthSate.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }
    fun signupAuthState(
        name: String, email: String, password: String,
        phoneNumber: String, uploadedImageUrls: String, role: String,
        onResult: (String) -> Unit
    ) {
        if (email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            _authState.value = AuthSate.Error("Email, password, or phone number can't be empty")
            return
        }
        _authState.value = AuthSate.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId.isNullOrEmpty()) {
                        _authState.value = AuthSate.Error("User ID is null or empty")
                        return@addOnCompleteListener
                    }

                    if (email.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
                        _authState.value = AuthSate.Error("Thiếu thông tin người dùng")
                        return@addOnCompleteListener
                    }
                    apiCallViewModel.updateIdAuth(email, userId)
                    val userModel =
                        UserAuthModel(
                            idauth = userId,
                            name = name,
                            email = email,
                            password = password,
                            phone = phoneNumber,
                            url = uploadedImageUrls,
                            role = role
                        )
                    FirebaseFirestore.getInstance().collection("users")
                        .document(userId)
                        .set(userModel)
                        .addOnSuccessListener {
                            checkAuthSate()
                        }
                        .addOnFailureListener { e ->
                            _authState.value =
                                AuthSate.Error(e.message ?: "Failed to save user data")
                        }
                    onResult("Đăng ký thành công")
                } else {
                    _authState.value =
                        AuthSate.Error(task.exception?.message ?: "Something went wrong")
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
                    val userData = document.toObject(UserAuthModel::class.java)
                    _user.value = userData
                } else {
                    _user.value = null
                }
            }
            .addOnFailureListener { exception ->
                _user.value = null
            }
    }

    fun updateUserAccount(
        name: String? = null,
        email: String? = null,
        gender: String? = null,
        phone: String? = null,
        dateBirth: String? = null,
        uploadedImageUrl: String? = null,
        role: String? = null,
        onComplete: (Boolean, String) -> Unit
    ) {
        val user = auth.currentUser
        if (user == null) {
            _authState.value = AuthSate.Unauthenticated
            return
        }
        _authState.value = AuthSate.Loading
        viewModelScope.launch {
            try {
                if (email != null && email != user.email) {
                    user.updateEmail(/* p0 = */ email).await()
                }
                if (name != null) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user.updateProfile(profileUpdates).await()
                    Log.d("AuthViewModel", "Đã cập nhật profile Auth thành công.")
                }
                val updates = mutableMapOf<String, Any>()
                if (name != null) updates["name"] = name
                if (gender != null) updates["gender"] = gender
                if (phone != null) updates["phone"] = phone
                if (dateBirth != null) updates["dateBirth"] = dateBirth
                if (uploadedImageUrl != null) updates["url"] = uploadedImageUrl
                if (role != null) updates["role"] = role

                if (updates.isNotEmpty()) {
                    db.collection("users").document(user.uid)
                        .update(updates)
                        .await()
                    Log.d("AuthViewModel", "Đã cập nhật dữ liệu Firestore thành công.")
                }
                _authState.value = AuthSate.User
                getCurrentUser()
                onComplete(true, "Cập nhật tài khoản thành công.")
            } catch (e: Exception) {
                _authState.value = AuthSate.Error(e.message ?: "Lỗi không xác định khi cập nhật.")
                onComplete(false, e.message ?: "Lỗi không xác định khi cập nhật.")
            }
        }
    }
    fun signOut() {
        auth.signOut()
        _authState.value = AuthSate.Unauthenticated
    }

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }
    suspend fun getSuspendingUserFullName(): String? {
        return try {
            val userId = auth.currentUser?.uid ?: return null
            val document = db.collection("users").document(userId).get().await()
            document.getString("name")
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getSuspendingUrl(): String? {
        return try {
            val userId = auth.currentUser?.uid ?: return null
            val document = db.collection("users").document(userId).get().await()
            document.getString("url")
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getSuspendingPoint(): String? {
        return try {
            val userId = auth.currentUser?.uid ?: return null
            val document = db.collection("users").document(userId).get().await()
            document.getString("bonuspoint")
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getSuspendingEmail(): String? {
        return try {
            val userId = auth.currentUser?.uid ?: return null
            val document = db.collection("users").document(userId).get().await()
            document.getString("email")
        } catch (e: Exception) {
            null
        }
    }
    fun getUserPhone(onResult: (String?) -> Unit) {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { onResult(it.getString("phoneNumber")) }
                .addOnFailureListener { onResult(null) }
        } ?: onResult(null)
    }
    fun getEmail(onResult: (String?) -> Unit) {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { onResult(it.getString("email")) }
                .addOnFailureListener { onResult(null) }
        } ?: onResult(null)
    }
    fun getUrl(onResult: (String?) -> Unit) {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { onResult(it.getString("url")) }
                .addOnFailureListener { onResult(null) }
        } ?: onResult(null)
    }
}
sealed class AuthSate {
    data object Unauthenticated : AuthSate()
    data object Loading : AuthSate()
    data object Admin : AuthSate()
    data object User : AuthSate()
    data class Error(var message: String) : AuthSate()
}