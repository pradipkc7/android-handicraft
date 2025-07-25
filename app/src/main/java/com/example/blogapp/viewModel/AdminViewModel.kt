package com.example.blogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogapp.repository.UserRepository

class AdminViewModel(val repo: UserRepository) : ViewModel() {
    
    private val _isAdmin = MutableLiveData<Boolean>(false)
    val isAdmin: LiveData<Boolean> get() = _isAdmin
    
    private val _adminLoginStatus = MutableLiveData<Boolean>(false)
    val adminLoginStatus: LiveData<Boolean> get() = _adminLoginStatus
    
    fun checkAdminAccess(username: String, password: String) {
        // Admin credentials check
        if (username == "admin" && password == "admin123") {
            _isAdmin.postValue(true)
            _adminLoginStatus.postValue(true)
        } else {
            _isAdmin.postValue(false)
            _adminLoginStatus.postValue(false)
        }
    }
    
    fun logoutAdmin() {
        _isAdmin.postValue(false)
        _adminLoginStatus.postValue(false)
    }
    
    fun isUserAdmin(): Boolean {
        return _isAdmin.value ?: false
    }
} 