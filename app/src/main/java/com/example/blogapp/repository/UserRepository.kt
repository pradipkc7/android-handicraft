package com.example.blogapp.repository

import com.example.blogapp.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    
    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit)
    
    fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callBack: (Boolean, String) -> Unit
    )
    
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)
    
    fun editProfile(
        userId: String,
        data: MutableMap<String, Any>,
        callBack: (Boolean, String) -> Unit
    )
    
    fun getCurrentUser(): FirebaseUser?
    
    fun getUserFromDatabase(
        userId: String,
        callBack: (Boolean, String, UserModel?) -> Unit
    )
    
    fun getAllUsers(callBack: (Boolean, String, List<UserModel?>) -> Unit)
    
    fun logout(callback: (Boolean, String) -> Unit)
    
    fun deleteAccount(
        userId: String,
        callBack: (Boolean, String) -> Unit
    )
} 