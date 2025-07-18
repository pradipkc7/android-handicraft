package com.example.blogapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blogapp.model.UserModel
import com.example.blogapp.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepository): ViewModel (){
    fun login(email:String,password:String,callback: (Boolean,String) -> Unit){
        repo.login(email,password,callback)
    }
    //authentication ko function
//    {
    // "success":true
    // "message":"register success"
    // userid":"shihsujd837663"
//     status:200}

    fun register(email:String,password:String,callback: (Boolean,String,String) -> Unit)
    { repo.register(email,password,callback) }

    //real time database ko function
    fun addUserToDatabase(
        userId: String,
        model:UserModel,
        callBack:(Boolean,String)-> Unit
    ){ repo.addUserToDatabase(userId,model,callBack) }

    fun forgetPassword(email:String,callback: (Boolean,String) -> Unit){repo.forgetPassword(email,callback)}
    fun editProfile(userId: String,
                    data: MutableMap<String, Any>,
                    callBack:(Boolean,String,)-> Unit){repo.editProfile(userId,data,callBack)}

    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }
    
    private val _users = MutableLiveData<UserModel?>()
    val users: LiveData<UserModel?> get() = _users
    
    private val _allUsers = MutableLiveData<List<UserModel?>>()
    val allUsers: LiveData<List<UserModel?>> get() = _allUsers
    
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    
    fun getUserFromDatabase(userId: String,
                            callBack:(Boolean, String, UserModel?)-> Unit){ repo.getUserFromDatabase(userId){
                                success,message,users->
                                if(success){
                                    _users.postValue(users)
                                }else{
                                    _users.postValue(null)
                                }
    }}
    
    fun getAllUsers() {
        _loading.postValue(true)
        repo.getAllUsers { success, message, users ->
            if (success) {
                _loading.postValue(false)
                _allUsers.postValue(users)
            } else {
                _loading.postValue(false)
                _allUsers.postValue(emptyList())
            }
        }
    }
    
    fun logout(callback: (Boolean,String) -> Unit){ repo.logout(callback)}
    fun deleteAccount(userId: String,
                      callBack:(Boolean,String)-> Unit){ repo.deleteAccount(userId,callBack)}
}